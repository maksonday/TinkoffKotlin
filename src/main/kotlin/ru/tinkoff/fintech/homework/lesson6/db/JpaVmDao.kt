package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm

@Profile("jpa")
@Service
class JpaVmDao(@Autowired private val repo: JpaVmRepository) : VmDao {
    override fun getById(id: Int): Vm {
        TODO("Not yet implemented")
    }

    override fun create(type: String, image: Image, config: Config): Int {
        TODO("Not yet implemented")
    }

    override fun getList(osType: String, rows: Int, page: Int): List<Vm> {
        TODO("Not yet implemented")
    }
}