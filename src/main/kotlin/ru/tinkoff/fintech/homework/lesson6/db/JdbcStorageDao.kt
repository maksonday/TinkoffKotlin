package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import java.sql.SQLException

@Profile("jdbc")
@Service
class JdbcStorageDao(private val jdbcTemplate: JdbcTemplate) : StorageDao {
    override fun getConfig(id: Int): Config {
        val dataSource = jdbcTemplate.dataSource
        if (dataSource != null) {
            try {
                val selectSql = "select * from configs where id = ? limit 1"
                val ps = dataSource.connection.prepareStatement(selectSql)
                ps.setInt(1, id)
                val rs = ps.executeQuery()
                if (rs.next()) {
                    return Config(
                        rs.getInt("id"),
                        rs.getInt("diskSize"),
                        rs.getInt("cores"),
                        rs.getInt("sizeRam")
                    )
                } else throw IllegalArgumentException("No config with this id")
            } catch (e: SQLException) {
                throw Exception(e.message)
            }
        } else throw Exception("Couldn't establish connection")
    }

    override fun getImg(id: Int): Image {
        val dataSource = jdbcTemplate.dataSource
        if (dataSource != null) {
            try {
                val selectSql = "select * from images where id = ? limit 1"
                val ps = dataSource.connection.prepareStatement(selectSql)
                ps.setInt(1, id)
                val rs = ps.executeQuery()
                if (rs.next()) {
                    return Image(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("diskSizeRequirements"),
                        rs.getInt("ramRequirements"),
                    )
                } else throw IllegalArgumentException("No image with this id")
            } catch (e: SQLException) {
                throw Exception(e.message)
            }
        } else throw Exception("Couldn't establish connection")
    }
}