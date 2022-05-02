package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image

@Profile("jpa")
@Service
class JpaStorageDao(
    private val imageRepo: JpaImageRepository? = null,
    private val configRepo: JpaConfigRepository? = null
) : StorageDao {
    override fun getConfig(id: Int): Config {
        try {
            return configRepo!!.getConfigById(id)
        } catch (e: Exception) {
            throw IllegalArgumentException("No config with this id")
        }
    }

    override fun getImg(id: Int): Image {
        try {
            return imageRepo!!.getImageById(id)
        } catch (e: Exception) {
            throw IllegalArgumentException("No image with this id")
        }
    }


}