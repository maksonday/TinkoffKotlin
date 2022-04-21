package ru.tinkoff.fintech.homework.lesson6.vm.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.spec.style.Test
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.webjars.NotFoundException
import ru.tinkoff.fintech.homework.lesson6.db.JdbcStorageDao
import ru.tinkoff.fintech.homework.lesson6.db.JdbcVmDao
import ru.tinkoff.fintech.homework.lesson6.db.VmDao
import ru.tinkoff.fintech.homework.lesson6.vm.KvmController
import ru.tinkoff.fintech.homework.lesson6.vm.model.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.*
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.Storage
import java.lang.ref.Cleaner.create
import javax.sql.DataSource
import kotlin.random.Random.Default.nextInt
import kotlin.test.assertEquals
import kotlin.text.Charsets.UTF_8

@ActiveProfiles("jdbc")
@Sql("schema.sql", "data.sql")
@JdbcTest
open class JdbcTest(@Autowired private var jdbcTemplate : JdbcTemplate) : FeatureSpec() {
    private val jdbcVmDao = JdbcVmDao(jdbcTemplate)
    private val jdbcStorageDao = JdbcStorageDao(jdbcTemplate)
    private val controller = KvmController(KvmService(KvmManager(jdbcVmDao, jdbcStorageDao)))

    init {
        feature("create kvm") {
            scenario("success") {
                val request = CreateVmRequest("kvm", 1, 1)

                val result = controller.createKvm(request)

                result should{
                    it.item shouldBe 2
                    it.status shouldBe VmManagerStatus.IN_PROGRESS
                    it.comment shouldBe null
                }
            }

            scenario("failed - no image with this id") {
                val request = CreateVmRequest("kvm", 4, 1)

                val result = controller.createKvm(request)

                result should{
                    it.item shouldBe null
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "No image with this id"
                }
            }

            scenario("failed - no config with this id") {
                val request = CreateVmRequest("kvm", 1, 4)

                val result = controller.createKvm(request)

                result should{
                    it.item shouldBe null
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "No config with this id"
                }
            }

            scenario("fail - incompatible system requirements") {
                val request = CreateVmRequest("kvm", 1, 2)

                val result = controller.createKvm(request)

                result should{
                    it.item shouldBe null
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "Incompatible system requirements"
                }
            }
        }

        feature("get kvm by id") {
            scenario("success") {
                val request = 1

                val result = controller.getKvmById(request)

                result should{
                    it.item shouldBe Kvm("kvm", 1, 1, 1, "Linux", VmState.OFF, VmStatus.DISK_DETACHED)
                    it.status shouldBe VmManagerStatus.READY
                    it.comment shouldBe null
                }
            }

            scenario("fail") {
                val request = 200

                val result = controller.getKvmById(request)

                result should{
                    it.item shouldBe null
                    it.status shouldBe null
                    it.comment shouldBe "VM with this ID doesn't exist"
                }
            }
        }

        feature("get kvm list with pagination and osType") {
            scenario("success - found some fitting results") {
                for(i in 1..10){
                    controller.createKvm(CreateVmRequest("kvm", ((1..3).random()), ((1..3).random())))
                }
                val result = controller.getKvmListWithParams("Linux", 10, 1)

                result.size shouldBe 10

                for(i in result){
                    i should {
                        it.type shouldBe "kvm"
                        it.configId shouldNotBe null
                        it.imageId shouldNotBe null
                        it.osType shouldBe "Linux"
                        it.state shouldBe VmState.OFF
                        it.vmStatus shouldBe VmStatus.DISK_DETACHED
                    }
                }
            }

            scenario("fail - not found any fitting results") {
                val result = controller.getKvmListWithParams("Linux", 15, 2)

                result.size shouldBe 0
            }
        }
    }
}