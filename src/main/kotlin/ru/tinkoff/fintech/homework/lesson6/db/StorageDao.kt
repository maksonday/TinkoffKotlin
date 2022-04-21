package ru.tinkoff.fintech.homework.lesson6.db

import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image

interface StorageDao {
    fun getConfig(id: Int): Config
    fun getImg(id: Int): Image
}