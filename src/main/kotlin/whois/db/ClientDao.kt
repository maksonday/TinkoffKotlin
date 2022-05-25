package whois.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import whois.model.Client
import java.sql.Statement

@Profile("whois")
@Service
class ClientDao(@Autowired private val jdbcTemplate: JdbcTemplate) {
    fun getClientByDomainId(domainId: Int): Client? =
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val selectSql = "select * from clients where domain_id = ?"
                val ps = it.prepareStatement(selectSql)
                ps.setInt(1, domainId)
                val rs = ps.executeQuery()
                if (rs.next()) {
                    Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("localAddress"),
                        rs.getString("phone"),
                        rs.getString("fax"),
                        rs.getString("email"),
                        rs.getInt("domain_id")
                    )
                } else null
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }

    fun getClientById(id: Int): Client? =
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val selectSql = "select * from clients where id = ?"
                val ps = it.prepareStatement(selectSql)
                ps.setInt(1, id)
                val rs = ps.executeQuery()
                if (rs.next()) {
                    Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("fax"),
                        rs.getString("email"),
                        rs.getInt("domain_id")
                    )
                } else null
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }

    fun create(client: Client): Int? =
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val insertSql = "insert into clients(name, address, phone, fax, email, domain_id) " +
                        "values (?, ?, ?, ?, ?, ?)"
                val ps = it.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)
                ps.setString(1, client.name)
                ps.setString(2, client.address)
                ps.setString(3, client.phone)
                ps.setString(4, client.fax)
                ps.setString(5, client.email)
                ps.setInt(6, client.domainId!!)
                ps.executeUpdate()
                val rs = ps.generatedKeys
                if (rs.next()) {
                    rs.getInt(1)
                } else null
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
}