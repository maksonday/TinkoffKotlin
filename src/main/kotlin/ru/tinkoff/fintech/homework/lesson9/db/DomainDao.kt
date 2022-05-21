package ru.tinkoff.fintech.homework.lesson9.db

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson9.domain.model.Domain

@Service
class DomainDao(private val repo: DomainRepository? = null) {
    fun getById(id: Int): Domain {
        try {
            return repo!!.getDomainById(id)
        } catch (e: Exception) {
            throw IllegalArgumentException("Domain with this ID doesn't exist")
        }
    }

    fun create(name: String): Int {
        try {
            val obj = repo!!.save(Domain(null, name, null, null, null, null))
            return obj.id!!
        } catch (e: Exception) {
            throw Exception("Unable to create VM")
        }
    }

    fun update(id: Int, created: String?, fetched: String?, paidTill: String?, freeDate: String?) {
        try {
            val domain = repo?.getDomainById(id)
            if (domain != null) {
                domain.created = created
                domain.fetched = fetched
                domain.paidTill = paidTill
                domain.freeDate = freeDate
                repo?.save(domain)
            } else {
                throw Exception("Domain not found")
            }
        } catch (e: Exception) {
            throw Exception("Unable to update domain info")
        }
    }

    fun delete(id: Int) {
        try {
            val domain = repo?.getDomainById(id)
            if (domain != null) {
                repo?.delete(domain)
            } else {
                throw Exception("Domain not found")
            }
        } catch (e: Exception) {
            throw Exception("Unable to delete domain")
        }
    }
}