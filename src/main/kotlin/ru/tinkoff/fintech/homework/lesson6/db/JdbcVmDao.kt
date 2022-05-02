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
        val dataSource = jdbcTemplate.dataSource
        if (dataSource != null) {
            try {
                val selectSql = "select * from vm where id = ? limit 1"
                val ps = dataSource.connection.prepareStatement(selectSql)
                ps.setInt(1, id)
                val rs = ps.executeQuery()
                if (rs.next()) {
                    return Kvm(
                        rs.getString("type"),
                        rs.getInt("id"),
                        rs.getInt("imageId"),
                        rs.getInt("configId"),
                        rs.getString("osType"),
                        VmState.valueOf(rs.getString("state")),
                        VmStatus.valueOf(rs.getString("status"))
                    )
                } else throw IllegalArgumentException("VM with this ID doesn't exist")
            } catch (e: SQLException) {
                throw Exception(e.message)
            }
        } else throw Exception("Couldn't establish connection")
    }

    override fun create(type: String, image: Image, config: Config, osType: String): Int {
        val dataSource = jdbcTemplate.dataSource
        if (dataSource != null) {
            try {
                val insertSql = "insert into vm(type, imageId, configId, osType, state, status) " +
                        "values (?, ?, ?, ?, 'OFF', 'DISK_DETACHED')"
                val ps = dataSource.connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, type)
                ps.setInt(2, image.id!!)
                ps.setInt(3, config.id!!)
                ps.setString(4, osType)
                ps.executeUpdate()
                val rs = ps.generatedKeys
                if (rs.next()) {
                    return rs.getInt(1)
                } else throw Exception("Unable to create kvm")
            } catch (e: SQLException) {
                throw Exception(e.message)
            }
        } else throw Exception("Couldn't establish connection")
    }

    override fun getList(osType: String, rows: Int, page: Int): List<Vm> {
        val response = mutableListOf<Vm>()
        val dataSource = jdbcTemplate.dataSource
        if (dataSource != null) {
            try {
                val selectSql = "select * from vm where osType = ? limit ? offset ?"
                val ps = dataSource.connection.prepareStatement(selectSql)
                ps.setString(1, osType)
                ps.setInt(2, rows)
                ps.setInt(3, rows * (page - 1))
                val rs = ps.executeQuery()
                while (rs.next()) {
                    response.add(
                        Kvm(
                            rs.getString("type"),
                            rs.getInt("id"),
                            rs.getInt("imageId"),
                            rs.getInt("configId"),
                            rs.getString("osType"),
                            VmState.valueOf(rs.getString("state")),
                            VmStatus.valueOf(rs.getString("status"))
                        )
                    )
                }
            } catch (e: SQLException) {
                throw Exception(e.message)
            }
        } else throw Exception("Couldn't establish connection")
        return response
    }
}