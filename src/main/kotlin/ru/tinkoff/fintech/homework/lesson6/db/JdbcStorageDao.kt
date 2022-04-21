package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image

@Profile("jdbc")
@Service
class JdbcStorageDao(private val jdbcTemplate: JdbcTemplate) : StorageDao {
    override fun getConfig(id: Int): Config {
        val response = jdbcTemplate.query("select * from configs where id = $id limit 1") { rs, _ ->
            Config(
                rs.getInt("id"),
                rs.getInt("diskSize"),
                rs.getInt("cores"),
                rs.getInt("sizeRam"),
            )
        }
        if (response.size != 0){
            return response[0]
        }
        else throw IllegalArgumentException("No config with this id")
    }

    override fun getImg(id: Int): Image {
        val response =  jdbcTemplate.query("select * from images where id = $id limit 1") { rs, _ ->
            Image(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getInt("diskSizeRequirements"),
                rs.getInt("ramRequirements"),
            )
        }
        if (response.size != 0){
            return response[0]
        }
        else throw IllegalArgumentException("No image with this id")
    }
}