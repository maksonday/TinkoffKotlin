package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KvmListClient
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.Storage

@Service
class VmManager(
    private val kvmListClient: KvmListClient,
    private val storage: Storage
) {
    fun getList(osType: String, rows: Int, page: Int): List<Kvm> = kvmListClient.getList(osType, rows, page)

    fun getById(id: Int) = kvmListClient.getById(id)


    fun create(imgId: Int, configId: Int): Int? {
        val image = getImageById(imgId)
        val config = getConfigById(configId)
        return kvmListClient.create("kvm", image, config)
    }

    private fun getConfigById(id: Int): Config = storage.getConfig(id)

    private fun getImageById(id: Int): Image = storage.getImg(id)
}