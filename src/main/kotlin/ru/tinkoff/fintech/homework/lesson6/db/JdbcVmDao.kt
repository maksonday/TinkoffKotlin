package ru.tinkoff.fintech.homework.lesson6.db

import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus
import java.sql.SQLException
import java.sql.Statement

@Primary
@Profile("jdbc")
@Service
class JdbcVmDao(private val jdbcTemplate: JdbcTemplate) : VmDao {
    override fun getById(id: Int): Vm {
        val response = jdbcTemplate.query("select * from vm where id = $id limit 1") { rs, _ ->
            Kvm(
                rs.getString("type"),
                rs.getInt("id"),
                rs.getInt("imageId"),
                rs.getInt("configId"),
                rs.getString("osType"),
                VmState.valueOf(rs.getString("state")),
                VmStatus.valueOf(rs.getString("status"))
            )
        }
        if (response.size != 0) {
            return response[0]
        } else throw IllegalArgumentException("VM with this ID doesn't exist")
    }

    override fun create(type: String, image: Image, config: Config): Int {
        val dataSource = jdbcTemplate.dataSource
        if (dataSource != null) {
            try {
                val insertSql = "insert into vm(type, imageId, configId, osType, state, status) " +
                        "values (?, ?, ?, ?, 'OFF', 'DISK_DETACHED')"
                val ps = dataSource.connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, type)
                ps.setInt(2, image.id!!)
                ps.setInt(3, config.id!!)
                ps.setString(4, "Linux")
                ps.executeUpdate()
                try {
                    val rs = ps.generatedKeys
                    if (rs.next()) {
                        return rs.getInt(1)
                    }
                } catch (s: SQLException) {
                    s.printStackTrace()
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        } else throw Exception("Couldn't establish connection")
        throw Exception("Unable to create kvm")
    }

    override fun getList(osType: String, rows: Int, page: Int): List<Vm> {
        return jdbcTemplate.query("select * from vm where osType = '$osType' limit $rows offset $rows * ($page - 1)") { rs, _ ->
            Kvm(
                rs.getString("type"),
                rs.getInt("id"),
                rs.getInt("imageId"),
                rs.getInt("configId"),
                rs.getString("osType"),
                VmState.valueOf(rs.getString("state")),
                VmStatus.valueOf(rs.getString("status"))
            )
        }
    }

}