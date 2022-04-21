package ru.tinkoff.fintech.homework.lesson6.vm.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForEntity
import org.webjars.NotFoundException
import ru.tinkoff.fintech.homework.lesson6.db.VmDao
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateVmResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

@Profile("dev", "test")
@Service
class KvmListClient(
    private val restTemplate: RestTemplate,
    @Value("\${kvm.list.address}") private val kvmListAddress: String
) : VmDao {
    companion object {
        private const val GET_KVM_LIST = "/kvm_list?os_type={osType}&rows={rows}&page={page}"
        private const val GET_KVM_BY_ID = "/kvm?id={id}"
        private const val CREATE_KVM = "/kvm/create"
    }

    override fun getList(osType: String, rows: Int, page: Int): List<Kvm> {
        return restTemplate.getForObject(
            "$kvmListAddress$GET_KVM_LIST",
            osType.lowercase(), rows, page
        )
    }

    override fun getById(id: Int): Kvm = try {
        restTemplate.getForObject("$kvmListAddress$GET_KVM_BY_ID", id)
    } catch (e: NotFound) {
        throw NotFoundException("Kvm with id = $id doesn't exist")
    }

    override fun create(type: String, image: Image, config: Config): Long {
        try {
            val kvm =
                Kvm(
                    type,
                    null,
                    image.id,
                    config.id,
                    "Linux",
                    VmState.OFF,
                    VmStatus.DISK_DETACHED
                )
            val request = CreateVmResponse(kvm)
            val id = restTemplate.postForEntity<Kvm>("$kvmListAddress$CREATE_KVM", request).body?.id
            if (id != null) return id
            else throw Exception("Unable to create kvm")
        } catch (e: Exception) {
            throw Exception("Unable to create kvm")
        }
    }
}