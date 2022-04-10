package ru.tinkoff.fintech.homework.lesson6.vm

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import ru.tinkoff.fintech.homework.lesson6.vm.model.*
import ru.tinkoff.fintech.homework.lesson6.vm.service.KVMService
import ru.tinkoff.fintech.homework.lesson6.vm.service.VMManager
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KVMListClient

class VmControllerTest {

    private val kvmSet = setOf<KVM>(
        KVM(
            "kvm",
            1,
            Image("Ubuntu 20.04", "Linux", 8, 8),
            Config(100, 4, 8),
            "Linux",
            State.RUNNING,
            Status.DISK_ATTACHED
        ),
        KVM(
            "kvm",
            2,
            Image("Ubuntu 16.04", "Linux", 8, 8),
            Config(250, 2, 8),
            "Linux",
            State.RUNNING,
            Status.DISK_ATTACHED
        ),
        KVM(
            "kvm",
            3,
            Image("Debian 7", "Linux", 4, 4),
            Config(10, 2, 4),
            "Linux",
            State.RUNNING,
            Status.DISK_ATTACHED
        ),
        KVM(
            "kvm",
            4,
            Image("Debian 6", "Linux", 4, 4),
            Config(10, 2, 4),
            "Linux",
            State.OFF,
            Status.DISK_DETACHED
        ),
        KVM(
            "kvm",
            5,
            Image("Fedora", "Linux", 4, 4),
            Config(10, 2, 4),
            "Linux",
            State.BOOT,
            Status.DISK_ATTACHED
        ),
    )

    @Test
    fun `getKvmList(state) должен вернуть список всех созданных kvm, состояние которых соответствует state`() {
        val client = mockk<KVMListClient>(relaxed = true)
        val controller = VmController(VMManager(KVMService(client)))
        every { client.getList(State.RUNNING) } returns setOf<KVM>(
            KVM(
                "kvm",
                1,
                Image("Ubuntu 20.04", "Linux", 8, 8),
                Config(100, 4, 8),
                "Linux",
                State.RUNNING,
                Status.DISK_ATTACHED
            ),
            KVM(
                "kvm",
                2,
                Image("Ubuntu 16.04", "Linux", 8, 8),
                Config(250, 2, 8),
                "Linux",
                State.RUNNING,
                Status.DISK_ATTACHED
            ),
            KVM(
                "kvm",
                3,
                Image("Debian 7", "Linux", 4, 4),
                Config(10, 2, 4),
                "Linux",
                State.RUNNING,
                Status.DISK_ATTACHED
            )
        )

        val response = controller.getKvmList(State.RUNNING)

        assertAll(
            { verify(exactly = 1) { controller.getKvmList(State.RUNNING) } },
            {
                assertEquals(
                    kvmSet.filter { it -> it.state == State.RUNNING }.toSet(), response
                )
            }
        )
    }

    @Test
    fun createKvm() {
    }

    @Test
    fun `getKvmById(id) должен вернуть KVM с заданным id, если она есть в базе`() {
        val client = mockk<KVMListClient>(relaxed = true)
        val controller = VmController(VMManager(KVMService(client)))
        every { client.getById(4) } returns KVM(
            "kvm",
            4,
            Image("Debian 6", "Linux", 4, 4),
            Config(10, 2, 4),
            "Linux",
            State.OFF,
            Status.DISK_DETACHED
        )

        val response = controller.getKvmById(4)

        assertAll(
            { verify(exactly = 1) { controller.getKvmById(4) } },
            {
                assertEquals(
                    kvmSet.elementAt(3), response
                )
            }
        )
    }
        @Test
        fun `getKvmById(id) должен выкинуть исключине, если ее нет в базе`() {
            val client = mockk<KVMListClient>(relaxed = true)
            val controller = VmController(VMManager(KVMService(client)))
            every { client.getById(6) } returns null

            val exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
                controller.getKvmById(6)
            }

            assertAll(
                { verify(exactly = 1) { controller.getKvmById(6) } },
                {
                    assertEquals(
                        "Не существует KVM с данным id = 6", exception.message
                    )
                }
            )
        }
    }