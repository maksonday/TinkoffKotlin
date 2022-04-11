package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM
import ru.tinkoff.fintech.homework.lesson6.vm.model.State
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status

@Service
class KVMService(
    private val VMManager: VMManager,
) {
    fun getKvmList(state: State): Set<KVM> {
        return VMManager.getList(state)
    }

    fun create(imgId: Int, configId: Int) : CreateResponse<Int> =
        try {
            val id = VMManager.create(imgId, configId)
            CreateResponse(id, Status.IN_PROGRESS)
        } catch (e: Exception) {
            CreateResponse(null, Status.DECLINED, e.message)
        }

    fun getKvmById(id: Int): KVM = VMManager.getById(id)
}