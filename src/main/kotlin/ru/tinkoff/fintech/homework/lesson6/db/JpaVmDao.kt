package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.context.annotation.Profile
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

@Profile("jpa")
@Service
class JpaVmDao(private val repo: JpaVmRepository? = null) : VmDao {
    override fun getById(id: Int): Kvm {
        try {
            return repo!!.getKvmById(id)
        } catch (e: Exception) {
            throw IllegalArgumentException("VM with this ID doesn't exist")
        }
    }

    override fun create(type: String, image: Image, config: Config, osType: String): Int {
        try {
            val obj = repo!!.save(Kvm(type, null, image.id, config.id, osType, VmState.OFF, VmStatus.DISK_DETACHED))
            return obj.id!!
        } catch (e: Exception) {
            throw Exception("Unable to create VM")
        }
    }

    override fun getList(osType: String, rows: Int, page: Int): List<Kvm> {
        try {
            val pg = PageRequest.of(page - 1, rows)
            return repo!!.getKvmByOsType(osType, pg).toList()
        } catch (e: Exception) {
            throw IllegalArgumentException("No VMs with this osType")
        }
    }
}