package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus
import java.awt.Taskbar

@Profile("jpa")
@Service
class JpaVmDao : VmDao {
    @Autowired
    private val repo: JpaVmRepository? = null

    override fun getById(id: Int): Vm {
        lateinit var result : Vm
        try {
            result = repo!!.findById(id.toLong()).orElse(null)
        }
        catch (e : Exception){
            throw IllegalArgumentException(e.message)
        }
        if (result != null) return result
        else throw IllegalArgumentException("No kvm with this id")
    }

    override fun create(type: String, image: Image, config: Config): Long {
        try{
            val obj = repo!!.save(Kvm(type, null, image.id, config.id, "Linux", VmState.OFF, VmStatus.DISK_DETACHED))
            if (obj.id != null) return obj.id!!
            else throw Exception("Unable to create kvm")
        }
        catch (e : Exception){
            throw Exception(e.message)
        }
    }

    override fun getList(osType: String, rows: Int, page: Int): List<Vm> {
        TODO("Not yet implemented")
    }
}