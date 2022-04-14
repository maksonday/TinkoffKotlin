package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForEntity
import org.webjars.NotFoundException
import ru.tinkoff.fintech.homework.lesson6.configuration.ControllerExceptionHandler
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateVmResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

@Service
class KvmListClient(
    private val restTemplate: RestTemplate,
    @Value("\${kvm.list.address}") private val kvmListAddress: String
) {
    companion object {
        private const val GET_KVM_LIST = "/kvm_list?os_type={osType}&rows={rows}&page={page}"
        private const val GET_KVM_BY_ID = "/kvm?id={id}"
        private const val CREATE_KVM = "/kvm/create"
    }

    fun getList(osType: String, rows: Int, page: Int): List<Kvm> {
        return restTemplate.getForObject(
            "$kvmListAddress$GET_KVM_LIST",
            osType.lowercase(), rows, page
        )
    }

    fun getById(id: Int): Kvm? = try {
        restTemplate.getForObject("$kvmListAddress$GET_KVM_BY_ID", id)
    } catch (e: NotFound) {
        throw NotFoundException("Kvm with id = $id doesn't exist")
    }

    fun create(type: String, image: Image, config: Config): Int? {
        try {
            if (image.diskSizeRequirements > config.diskSize || image.ramRequirements > config.sizeRAM) throw Exception("Incompatible system requirements")
            val kvm =
                Kvm(
                    type,
                    null,
                    image,
                    config,
                    "Linux",
                    VmState.OFF,
                    VmStatus.DISK_DETACHED
                )
            val request = CreateVmResponse(kvm)
            return restTemplate.postForEntity<Kvm>("$kvmListAddress$CREATE_KVM", request).body?.id
        } catch (e: Exception) {
            throw Exception("Unable to create kvm")
        }
    }
}