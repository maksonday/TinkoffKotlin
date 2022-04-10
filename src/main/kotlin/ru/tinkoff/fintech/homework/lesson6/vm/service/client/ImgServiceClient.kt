package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM

@Service
class ImgServiceClient(
    private val restTemplate: RestTemplate,
    @Value("\${img.list.address}") private val imgListAddress: String
) {
    fun getList() : Set <Image> =
        restTemplate.exchange <Set <Image> > ("$imgListAddress$GET_KVM_LIST", HttpMethod.GET).body.orEmpty()

    fun getById(id : Int) : Image? = try {
        restTemplate.getForObject("$imgListAddress$GET_KVM_BY_ID", id)
    } catch (e: HttpClientErrorException.NotFound) {
        null
    }
}

private const val GET_KVM_LIST = "/img_list"
private const val GET_KVM_BY_ID = "/img/{id}"