package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KVMListClient
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.Storage

@Service
class VMManager(
    private val kvmListClient: KVMListClient,
    private val storage: Storage
) {
    fun getList(osType: String, rows: Int, page: Int): List<Kvm> = kvmListClient.getList(osType, rows, page)

    fun getById(id: Int): Kvm {
        val kvm = kvmListClient.getById(id)
        return requireNotNull(kvm) { "Не существует KVM с данным id = $id" }
    }

    fun create(imgId: Int, configId: Int): Int? {
        val image = getImageById(imgId)
        val config = getConfigById(configId)
        return kvmListClient.create("kvm", image, config)
    }

    private fun getConfigById(id: Int): Config = storage.getConfig(id)

    private fun getImageById(id: Int): Image = storage.getImg(id)
}