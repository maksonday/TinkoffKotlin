package ru.tinkoff.fintech.homework.lesson6.vm.service

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.tinkoff.fintech.homework.lesson6.vm.KvmController
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateVmRequest
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmManagerStatus
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

@ActiveProfiles("jdbc")
@SpringBootTest
class JdbcTest(private val controller: KvmController) : FeatureSpec() {
    init {
        feature("create kvm") {
            scenario("success") {
                val request = CreateVmRequest("kvm", 1, 1, "Linux")

                val result = controller.createKvm(request)

                result should {
                    it.item shouldBe 2
                    it.status shouldBe VmManagerStatus.IN_PROGRESS
                    it.comment shouldBe null
                }
            }

            scenario("failed - no image with this id") {
                val request = CreateVmRequest("kvm", 4, 1, "Linux")

                val result = controller.createKvm(request)

                result should {
                    it.item shouldBe null
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "No image with this id"
                }
            }

            scenario("failed - no config with this id") {
                val request = CreateVmRequest("kvm", 1, 4, "Linux")

                val result = controller.createKvm(request)

                result should {
                    it.item shouldBe null
                    it.status shouldBe VmManagerStatus.DECLINED
                    it.comment shouldBe "No config with this id"
                }
            }

            scenario("fail - incompatible system requirements") {
                val request = CreateVmRequest("kvm", 1, 2, "Linux")

                val result = controller.createKvm(request)

                result should {
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

                result should {
                    it.item shouldBe Kvm("kvm", 1, 1, 1, "Linux", VmState.OFF, VmStatus.DISK_DETACHED)
                    it.status shouldBe VmManagerStatus.READY
                    it.comment shouldBe null
                }
            }

            scenario("fail") {
                val request = 200

                val result = controller.getKvmById(request)

                result should {
                    it.item shouldBe null
                    it.status shouldBe null
                    it.comment shouldBe "VM with this ID doesn't exist"
                }
            }
        }

        feature("get kvm list with pagination and osType") {
            scenario("success - found some fitting results") {
                for (i in 1..10) {
                    controller.createKvm(CreateVmRequest("kvm", 1, 3, "Linux"))
                }
                val result = controller.getKvmListWithParams("Linux", 10, 1)

                result.size shouldBe 10

                for (i in result) {
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