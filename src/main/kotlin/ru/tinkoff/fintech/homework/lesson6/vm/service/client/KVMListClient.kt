package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForEntity
import ru.tinkoff.fintech.homework.lesson6.vm.model.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateVMRequest

@Service
class KVMListClient(
    private val restTemplate: RestTemplate,
    @Value("\${kvm.list.address}") private val kvmListAddress: String
) {
    fun getList(osType: String, rows: Int, page: Int): List<Kvm> {
        return restTemplate.getForObject(
            "$kvmListAddress$GET_KVM_LIST",
            osType.lowercase(), rows, page
        )
    }

    fun getById(id: Int): Kvm? = try {
        restTemplate.getForObject("$kvmListAddress$GET_KVM_BY_ID", id)
    } catch (e: NotFound) {
        null
    }

    fun create(type: String, image: Image, config: Config): Int? {
        val i: Int? = try {
            val kvm =
                Kvm(
                    type,
                    null,
                    image,
                    config,
                    "Linux",
                    State.OFF,
                    VMStatus.DISK_DETACHED
                )
            val request = CreateVMRequest(kvm)
            return restTemplate.postForEntity<Kvm>("$kvmListAddress$CREATE_KVM", request).body?.id
        } catch (e: Exception) {
            null
        }
        return i
    }
}

private const val GET_KVM_LIST = "/kvm_list?os_type={osType}&rows={rows}&page={page}"
private const val GET_KVM_BY_ID = "/kvm?id={id}"
private const val CREATE_KVM = "/kvm/create"