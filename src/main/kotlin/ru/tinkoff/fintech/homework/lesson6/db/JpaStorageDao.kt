package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image

@Profile("jpa")
@Service
class JpaStorageDao : StorageDao {
    @Autowired
    private val imageRepo: JpaImageRepository? = null

    @Autowired
    private val configRepo: JpaConfigRepository? = null

    override fun getConfig(id: Int): Config {
        val result = configRepo!!.findById(id.toLong()).orElse(null)
        if (result != null) return result
        else throw IllegalArgumentException("No image with this id")
    }

    override fun getImg(id: Int): Image {
        val result = imageRepo!!.findById(id.toLong()).orElse(null)
        if (result != null) return result
        else throw IllegalArgumentException("No image with this id")
    }


}