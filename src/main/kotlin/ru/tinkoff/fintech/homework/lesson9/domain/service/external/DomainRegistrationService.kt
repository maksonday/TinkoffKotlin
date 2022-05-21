package ru.tinkoff.fintech.homework.lesson9.domain.service.external

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import ru.tinkoff.fintech.homework.lesson9.domain.model.Domain

@Service
class DomainRegistrationService(@Value("\${registrator.address}") private val address: String) {
    private lateinit var webClient: WebClient

    suspend fun createDomain(id: Int, name: String): Domain? {
        return webClient.post().uri(address).body(Domain(id, name, null, null, null, null), Domain::class.java)
            .accept(MediaType.APPLICATION_JSON).retrieve().awaitBodyOrNull()
    }
}