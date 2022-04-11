package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM
import ru.tinkoff.fintech.homework.lesson6.vm.model.State
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KVMListClient
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.Storage

@Service
class VMManager(
    private val kvmListClient: KVMListClient,
    private val storage: Storage
) {
    private val db = HashMap<Int, KVM>()
    private val requests = HashMap<Int, Status>()

    fun getList(state: State): Set<KVM> = kvmListClient.getList(state)

    fun getById(id: Int): KVM {
        val kvm = kvmListClient.getById(id)
        return requireNotNull(kvm) { "Не существует KVM с данным id = $id" }
    }

    fun create(imgId: Int, configId: Int): Int? {
        val id = 100
        requests[id] = Status.IN_PROGRESS
        val image = getImageById(imgId)
        val config = getConfigById(configId)
        kvmListClient.create(id, "kvm", image, config)
        return id

    }

    private fun launch(kvm: KVM) {
        val time = 100L
        Thread.sleep(time)
    }

    private fun getConfigById(id: Int): Config = storage.getConfig(id)

    private fun getImageById(id: Int): Image = storage.getImg(id)
}