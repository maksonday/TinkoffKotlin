package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.db.StorageDao
import ru.tinkoff.fintech.homework.lesson6.db.VmDao
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm

@Service
class KvmManager(
    private val vmDao: VmDao,
    private val storage: StorageDao
) {
    fun getList(osType: String, rows: Int, page: Int): List<Vm> = vmDao.getList(osType, rows, page)

    fun getById(id: Int) = vmDao.getById(id)


    fun create(imgId: Int, configId: Int, osType: String): Int {
        val image = getImageById(imgId)
        val config = getConfigById(configId)
        if (image.diskSizeRequirements > config.diskSize || image.ramRequirements > config.sizeRAM) throw Exception(
            "Incompatible system requirements"
        )
        try {
            return vmDao.create("kvm", image, config, osType)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    private fun getConfigById(id: Int): Config = storage.getConfig(id)

    private fun getImageById(id: Int): Image = storage.getImg(id)
}