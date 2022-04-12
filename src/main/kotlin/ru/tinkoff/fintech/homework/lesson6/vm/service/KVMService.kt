package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status

@Service
class KVMService(
    private val VMManager: VMManager,
) {
    fun getKvmList(osType: String, rows: Int, page: Int): List<Kvm> {
        return VMManager.getList(osType, rows, page)
    }

    fun create(imgId: Int, configId: Int): CreateResponse<Int> =
        try {
            val id = VMManager.create(imgId, configId)
            CreateResponse(id, Status.IN_PROGRESS)
        } catch (e: Exception) {
            CreateResponse(null, Status.DECLINED, e.message)
        }

    fun getKvmById(id: Int): CreateResponse<Kvm> = try {
        CreateResponse(VMManager.getById(id), Status.READY)
    } catch (e: Exception) {
        CreateResponse(null, null, e.message)
    }
}