package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.GetConfigResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.GetImageResponse

@Repository
class Storage(
    private val restTemplate: RestTemplate,
    @Value("\${storage.address}") private val storageAddress: String
) {
    fun getImg(id: Int): Image = try {
        restTemplate.getForObject<GetImageResponse>("$storageAddress$GET_IMAGE", id).img
    } catch (e: HttpClientErrorException.NotFound) {
        throw NoSuchElementException("No image with this id")
    }

    fun getConfig(id: Int): Config = try {
        restTemplate.getForObject<GetConfigResponse>("$storageAddress$GET_CONFIG", id).config
    } catch (e: HttpClientErrorException.NotFound) {
        throw NoSuchElementException("No config with this id")
    }
}

private const val GET_IMAGE = "/image?id={id}"
private const val GET_CONFIG = "/config?id={id}"