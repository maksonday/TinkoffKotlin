package ru.tinkoff.fintech.homework.lesson6.db

import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm

interface VmDao {
    fun getById(id: Int): Vm
    fun create(type: String, image: Image, config: Config): Long
    fun getList(osType: String, rows: Int, page: Int): List<Vm>
}