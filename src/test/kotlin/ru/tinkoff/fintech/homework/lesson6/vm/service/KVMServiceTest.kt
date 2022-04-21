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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.webjars.NotFoundException
import ru.tinkoff.fintech.homework.lesson6.db.VmDao
import ru.tinkoff.fintech.homework.lesson6.vm.model.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.*
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.Storage
import kotlin.random.Random.Default.nextInt
import kotlin.text.Charsets.UTF_8

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class KVMServiceTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {
    @MockkBean
    private lateinit var vmDao: VmDao

    @MockkBean
    private lateinit var storage: Storage

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { vmDao.getList(any(), any(), any()) } answers {
            kvmStorage.filter { it.osType == firstArg() }
                .slice(secondArg<Int>() * (thirdArg<Int>() - 1) until secondArg<Int>() * thirdArg<Int>()).toList()
        }
        every { vmDao.getById(any()) } answers { kvmStorage.find { it.id == firstArg() }?: throw NotFoundException("Kvm with id = ${firstArg<Int>()} doesn't exist") }
        every { vmDao.create("kvm", any(), any()) } returns nextInt(1, 3)
        every { vmDao.create("kvm", Image(1,"Ubuntu 20.04", "Linux", 8, 8), Config(2, 10, 2, 4)) } throws Exception("Incompatible system requirements")
        every { vmDao.create("kvm", Image(1, "Ubuntu 20.04", "Linux", 8, 8), Config(1, 100, 4, 8)) } throws Exception("Unable to create kvm")
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
                val request = create(CreateVmRequest("kvm", 1, 0))

                request should {
                    it.item.shouldNotBeNull()
                    it.status shouldBe VmManagerStatus.IN_PROGRESS
                    it.comment shouldBe null
                }
                getKvmById(request.item!!) should {
                    it.item shouldBe kvmStorage.find { kvm -> kvm.id == request.item }
                    it.status shouldBe VmManagerStatus.READY
                }
            }
            scenario("fail - reached limit of kvm storage"){
                val request = create(CreateVmRequest("kvm", 0, 0))

                request should {
                    it.item.shouldBeNull()
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "Unable to create kvm"
                }
            }
            scenario("fail - incompatible system requirements"){
                val request = create(CreateVmRequest("kvm", 0, 1))

                request should {
                    it.item.shouldBeNull()
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "Incompatible system requirements"
                }
            }
            scenario("fail - image not found") {
                val request = create(CreateVmRequest("kvm", -1, 0))

                request should {
                    it.item.shouldBeNull()
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "No image with this id"
                }
            }
            scenario("fail - config not found") {
                val request = create(CreateVmRequest("kvm", 0, -1))

                request should {
                    it.item.shouldBeNull()
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "No config with this id"
                }
            }
        }
        feature("get kvm by id") {
            scenario("success") {
                val kvm = getKvmById(1)

                kvm should {
                    it.item shouldBe kvmStorage.find { kvm -> kvm.id == 1 }
                    it.status shouldBe VmManagerStatus.READY
                }
            }
            scenario("not found kvm with this id") {
                val kvm = getKvmById(4)

                kvm should {
                    it.item.shouldBeNull()
                    it.status.shouldBeNull()
                    it.comment shouldBe "Kvm with id = 4 doesn't exist"
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
            1,
            1,
            "Linux",
            VmState.RUNNING,
            VmStatus.DISK_ATTACHED
        ),
        Kvm(
            "kvm",
            2,
            2,
            3,
            "Linux",
            VmState.RUNNING,
            VmStatus.DISK_ATTACHED
        ),
        Kvm(
            "kvm",
            3,
            3,
            2,
            "Linux",
            VmState.RUNNING,
            VmStatus.DISK_ATTACHED
        )
    )

    companion object {
        private val images = mapOf(
            0 to Image(1, "Ubuntu 20.04", "Linux", 8, 8),
            1 to Image(2, "Ubuntu 16.04", "Linux", 8, 8),
            2 to Image(3, "Debian 7", "Linux", 4, 4)
        )

        private val configs = mapOf(
            0 to Config(1, 100, 4, 8),
            1 to Config(2, 10, 2, 4),
            2 to Config(3, 250, 4, 16),
        )
    }
}