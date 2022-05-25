package whois.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import whois.db.ClientDao
import whois.db.DomainDao
import whois.model.Domain
import whois.model.external.Response
import whois.service.external.Fetcher

@Profile("whois")
@Service
class DomainService(
    @Value("\${query.link}") private val queryLink: String,
    private val domainDao: DomainDao,
    private val clientDao: ClientDao,
    private val fetcher: Fetcher,
    private val clientService: ClientService
) {
    fun getByName(name: String): Response<Domain> {
        try {
            val domain = domainDao.getByName(name)
            if (domain == null) {
                val resp = fetcher.buildResponse(name, queryLink)
                if (resp.containsKey("domainInfo") && resp["domainInfo"] != null) {
                    val domainInfo = resp["domainInfo"]
                    val domainId = createDomain(name, domainInfo!!)
                    if (domainId != null) {
                        if (resp.containsKey("clientInfo") && resp["clientInfo"] != null) {
                            val clientInfo = resp["clientInfo"]!!
                            val client = clientDao.getClientByDomainId(domainId)
                            if (client == null) {
                                clientService.createClient(name, domainId, clientInfo)
                            }
                        }
                    } else {
                        throw Exception("Failed to fetch domain info")
                    }
                } else {
                    throw Exception("Failed to fetch domain info")
                }
                return Response(domainDao.getByName(name), null)
            } else {
                return Response(domain, null)
            }
        } catch (e: Exception) {
            return Response(null, e.message)
        }
    }

    private fun createDomain(name: String, data: Map<String, String>): Int? =
        try {
            domainDao.create(
                Domain(
                    null,
                    name,
                    data["inetnum"],
                    data["created"],
                    null,
                    null,
                    null,
                    data["mnt-by"],
                    data["admin-c"],
                    data["source"]
                )
            )
        } catch (e: Exception) {
            throw Exception("Error creating domain in DB: ${e.message}")
        }

    fun prolongDomain(name: String) =
        try {
            val domain = domainDao.getByName(name)
            if (domain != null) {
                val flag = domainDao.prolongDomain(domain)
                if (flag) {
                    Response(domain, "OK")
                } else {
                    Response(domain, "Error")
                }
            } else throw Exception("Domain with this name doesn't exist")
        } catch (e: Exception) {
            Response(null, e.message)
        }
}