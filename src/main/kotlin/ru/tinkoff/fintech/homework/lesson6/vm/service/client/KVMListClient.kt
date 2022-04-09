package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM

@Service
class KVMListClient(
    private val restTemplate: RestTemplate,
    @Value("\${kvm.list.address}") private val kvmListAddress: String
) {
    fun getList() : Set <KVM> =
        restTemplate.exchange <Set <KVM> > ("$kvmListAddress$GET_KVM_LIST", HttpMethod.GET).body.orEmpty()

    fun getById(id : Int) : KVM? = try {
        restTemplate.getForObject("$kvmListAddress$GET_KVM_BY_ID", id)
    } catch (e: NotFound) {
        null
    }
}

private const val GET_KVM_LIST = "/kvm_list"
private const val GET_KVM_BY_ID = "/kvm?id={id}"