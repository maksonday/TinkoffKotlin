package ru.tinkoff.fintech.homework.lesson6.vm.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import net.minidev.json.JSONObject
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import ru.tinkoff.fintech.homework.lesson6.vm.model.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KVMListClient
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.Storage
import kotlin.random.Random.Default.nextInt
import kotlin.text.Charsets.UTF_8

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class KVMServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {
    @MockkBean
    private lateinit var client: KVMListClient

    @MockkBean
    private lateinit var storage: Storage

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { client.getList(any(), any(), any()) } answers {
            kvmStorage.filter { it.osType == firstArg() }
                .slice(secondArg<Int>() * (thirdArg<Int>() - 1) until secondArg<Int>() * thirdArg<Int>()).toList()
        }
        every { client.getById(any()) } answers { kvmStorage.find { it.id == firstArg() } }
        every { client.create("kvm", any(), any()) } returns nextInt(1, 3)
        every { storage.getImg(any()) } answers {
            images[firstArg()] ?: throw NoSuchElementException("No image with this id")
        }
        every { storage.getConfig(any()) } answers {
            configs[firstArg()] ?: throw NoSuchElementException("No config with this id")
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("create kvm") {
            scenario("success") {
                val request = create(CreateVmRequest("kvm", 0, 0))

                request should {
                    it.item.shouldNotBeNull()
                    it.status shouldBe Status.IN_PROGRESS
                    it.comment shouldBe null
                }
                getKvmById(request.item!!) should {
                    it.item shouldBe kvmStorage.find { kvm -> kvm.id == request.item }
                    it.status shouldBe Status.READY
                }
            }
            scenario("fail - image not found") {
                val request = create(CreateVmRequest("kvm", -1, 0))

                request should {
                    it.item.shouldBeNull()
                    it.status shouldBe Status.DECLINED
                    it.comment shouldBe "No image with this id"
                }
            }
            scenario("fail - config not found") {
                val request = create(CreateVmRequest("kvm", 0, -1))

                request should {
                    it.item.shouldBeNull()
                    it.status shouldBe Status.DECLINED
                    it.comment shouldBe "No config with this id"
                }
            }
        }
        feature("get kvm by id") {
            scenario("success") {
                val kvm = getKvmById(1)

                kvm should {
                    it.item shouldBe kvmStorage.find { kvm -> kvm.id == 1 }
                    it.status shouldBe Status.READY
                }
            }
            scenario("not found kvm with this id") {
                val kvm = getKvmById(4)

                kvm should {
                    it.item.shouldBeNull()
                    it.status.shouldBeNull()
                    it.comment shouldBe "Не существует KVM с данным id = 4"
                }
            }
        }
        feature("get kvm list with params") {
            scenario("success") {
                val kvmList = getKvmListWithParams("Linux", 2, 1)

                kvmList shouldBe kvmStorage.filter { it.osType == "Linux" }.slice(0 until 2)
            }
        }
    }

    private fun create(request: CreateVmRequest, status: HttpStatus = HttpStatus.OK): CreateResponse<Int> =
        mockMvc.post("/vm/create") {
            content = ObjectMapper().writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
        }.readResponse(status)

    private fun getKvmById(id: Int): CreateResponse<Kvm> =
        mockMvc.get("/vm/kvm/{id}", id).readResponse()

    private fun getKvmListWithParams(osType : String, rows : Int?, page : Int) : List<Kvm> = mockMvc.get("/vm/kvm_list", osType, rows, page) {
            param("osType", osType)
            param("rows", rows.toString())
            param("page", page.toString())
        }.readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val kvmStorage = mutableListOf(
        Kvm(
            "kvm",
            1,
            Image("Ubuntu 20.04", "Linux", 8, 8),
            Config(100, 4, 8),
            "Linux",
            State.RUNNING,
            VMStatus.DISK_ATTACHED
        ),
        Kvm(
            "kvm",
            2,
            Image("Ubuntu 16.04", "Linux", 8, 8),
            Config(250, 2, 8),
            "Linux",
            State.RUNNING,
            VMStatus.DISK_ATTACHED
        ),
        Kvm(
            "kvm",
            3,
            Image("Debian 7", "Linux", 4, 4),
            Config(10, 2, 4),
            "Linux",
            State.RUNNING,
            VMStatus.DISK_ATTACHED
        )
    )

    private val images = mapOf(
        0 to Image("Ubuntu 20.04", "Linux", 8, 8),
        1 to Image("Ubuntu 16.04", "Linux", 8, 8),
        2 to Image("Debian 7", "Linux", 4, 4)
    )

    private val configs = mapOf(
        0 to Config(100, 4, 8),
        1 to Config(10, 2, 4),
        2 to Config(250, 4, 16),
    )
}