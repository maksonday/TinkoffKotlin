package ru.tinkoff.fintech.homework.lesson6.vm.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity.post
import org.springframework.mock.http.server.reactive.MockServerHttpRequest.post
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.tinkoff.fintech.homework.lesson6.vm.KvmController
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
    private lateinit var storage : Storage

    @MockkBean
    private lateinit var manager : VMManager

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { client.getList((any())) } answers { kvmStorage.toSet() }
        every { client.getById(any()) } answers { kvmStorage.find { it.id == firstArg() } }
        every { client.create(any(), "kvm", any(), any()) } answers { KVM( "kvm", firstArg(), secondArg(), thirdArg(), "Linux", State.OFF,
            VMStatus.DISK_DETACHED
        ) }
        every { storage.getImg(any()) } answers { images[firstArg()]?:throw NoSuchElementException("No image with this id") }
        every { storage.getConfig(any()) } answers { configs[firstArg()]?:throw NoSuchElementException("No image with this id") }
        //every { manager.create(any(), any())}  returns nextInt(0, 2)
        //every { manager.getById(any())} answers { kvmStorage.find { it.id == firstArg() }!! }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init{
        feature("create kvm"){
            scenario("success"){
                val request = create(CreateVmRequest("kvm", 0, 0))

                val kvmId = request.item
                val status = request.status
                kvmId.shouldNotBeNull()
                status shouldBe Status.IN_PROGRESS
                request.comment shouldBe null
                getKvmById(kvmId) should{
                    it.item!!.type shouldBe "kvm"
                    it.status shouldBe Status.READY
                }
            }
        }
    }

    private fun create(request : CreateVmRequest, status: HttpStatus = HttpStatus.OK): CreateResponse<Int> =
        mockMvc.post("/vm/create"){
            content = ObjectMapper().writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
        }.readResponse(status)

    private fun getKvmById(id: Int): CreateResponse<KVM> =
        mockMvc.get("/vm/kvm/{id}", id).readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    private val kvmStorage = mutableListOf(
        KVM(
            "kvm",
            1,
            Image("Ubuntu 20.04", "Linux", 8, 8),
            Config(100, 4, 8),
            "Linux",
            State.RUNNING,
            VMStatus.DISK_ATTACHED
        ),
        KVM(
            "kvm",
            2,
            Image("Ubuntu 16.04", "Linux", 8, 8),
            Config(250, 2, 8),
            "Linux",
            State.RUNNING,
            VMStatus.DISK_ATTACHED
        ),
        KVM(
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