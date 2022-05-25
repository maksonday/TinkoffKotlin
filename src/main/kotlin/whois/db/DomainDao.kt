package whois.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import whois.model.Domain
import whois.model.external.Response
import java.sql.Statement
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Profile("whois")
@Service
class DomainDao(@Autowired private val jdbcTemplate: JdbcTemplate) {
    fun getByName(name: String): Domain? =
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val selectSql = "select * from domains where name = ?"
                val ps = it.prepareStatement(selectSql)
                ps.setString(1, name)
                val rs = ps.executeQuery()
                if (rs.next()) {
                    Domain(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("ip"),
                        rs.getString("created"),
                        rs.getString("fetched"),
                        rs.getString("paid_till"),
                        rs.getString("free_date"),
                        rs.getString("registrar"),
                        rs.getString("admin"),
                        rs.getString("source")
                    )
                } else null
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }

    fun create(domain: Domain): Int? =
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val insertSql =
                    "insert into domains(name, ip, created, fetched, paid_till, free_date, registrar, admin, source) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                val ps = it.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)

                val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                val createdDate = LocalDate.parse(domain.created, inputFormatter).toString()
                ps.setString(1, domain.name)
                ps.setString(2, domain.ip)
                ps.setString(3, createdDate)
                ps.setString(4, LocalDate.now().toString())
                ps.setString(5, calcFreeDate(createdDate))
                ps.setString(6, calcFreeDate(createdDate))
                ps.setString(7, domain.registrar?.split(";")?.get(0))
                ps.setString(8, domain.admin?.split(";")?.get(0))
                ps.setString(9, domain.source)
                ps.executeUpdate()
                val rs = ps.generatedKeys
                if (rs.next()) {
                    rs.getInt(1)
                } else null
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }

    private fun calcFreeDate(date: String): String {
        val curDate = LocalDate.now().toString()
        val ourDate = date.replace("^(\\d+)".toRegex(), "2022")
        if (curDate >= ourDate) {
            return ourDate.replace("^(\\d+)".toRegex(), "2023")
        }
        return ourDate
    }

    fun prolongDomain(domain: Domain): Boolean {
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val updQuery = "update domains set paid_till = ?, free_date = ? where name = ?"
                val ps = it.prepareStatement(updQuery)
                val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
                val createdDate = LocalDate.parse(LocalDate.now().toString(), inputFormatter).toString()
                ps.setString(1, calcFreeDate(createdDate))
                ps.setString(2, calcFreeDate(createdDate))
                ps.setString(3, domain.name)
                val rs = ps.executeUpdate()
                return rs > 0
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun getDomainsToProlong(): List<Pair<String, List<String>>>? {
        val domainList = mutableListOf<Pair<String, List<String>>>()
        try {
            val dataSource = jdbcTemplate.dataSource ?: throw Exception("Unable to connect to DB")
            dataSource.connection.use {
                val selectSql =
                    "select c.name as client_name, d.name as domain_name, email, free_date from domains d right join clients c on d.id = c.domain_id where email is not null and c.name is not null and d.name is not null and d.free_date::DATE - now() < interval '10' day and free_date is not null order by d.id"
                val ps = it.prepareStatement(selectSql)
                val rs = ps.executeQuery()
                while (rs.next()) {
                    val clientName = rs.getString("client_name")
                    val domainName = rs.getString("domain_name")
                    val email = rs.getString("email")
                    val expireDate = rs.getString("free_date")
                    domainList.add(Pair(domainName, listOf(clientName, email, expireDate)))
                }
                return if (domainList.size > 0) {
                    domainList
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}