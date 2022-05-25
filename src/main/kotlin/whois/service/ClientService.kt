package whois.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import whois.db.ClientDao
import whois.model.Client
import whois.model.external.Response

@Profile("whois")
@Service
class ClientService(private val clientDao: ClientDao) {
    fun getById(id: Int) =
        try {
            val client = clientDao.getClientById(id)
            if (client != null) Response(client, null)
            else Response(null, "Client wih this ID doesn't exist")
        } catch (e: Exception) {
            Response(null, e.message)
        }

    fun createClient(name: String, domainId: Int, data: Map<String, String>): Int? =
        try {
            clientDao.create(
                Client(
                    null,
                    data["person"],
                    data["address"],
                    data["phone"],
                    data["fax-no"],
                    "abuse@$name",
                    domainId
                )
            )
        } catch (e: Exception) {
            throw Exception("Failed to create client in DB: ${e.message}")
        }
}