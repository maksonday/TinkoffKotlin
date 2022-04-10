package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.lesson6.vm.model.*

@Service
class KVMListClient(
    private val restTemplate: RestTemplate,
    @Value("\${kvm.list.address}") private val kvmListAddress: String
) {
    fun getList(state : State): Set <KVM> =
        restTemplate.exchange <Set <KVM> > ("$kvmListAddress$GET_KVM_LIST", HttpMethod.GET).body.orEmpty()

    fun getById(id : Int) : KVM? = try {
        restTemplate.getForObject("$kvmListAddress$GET_KVM_BY_ID", id)
    } catch (e: NotFound) {
        null
    }

    fun create(id : Int, type : String, image : Image, config : Config,) : Int? = try {
        val kvm = KVM(
            type,
            id,
            image,
            config,
            "Linux",
            State.OFF,
            Status.DISK_DETACHED
        )
        restTemplate.postForObject("$kvmListAddress$CREATE_KVM", kvm, ResponseEntity.class)
    } catch (e : )
}

private const val GET_KVM_LIST = "/kvm_list"
private const val GET_KVM_BY_ID = "/kvm?id={id}"
private const val CREATE_KVM = "/kvm/create"