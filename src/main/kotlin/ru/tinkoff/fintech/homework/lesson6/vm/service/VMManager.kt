package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM
import ru.tinkoff.fintech.homework.lesson6.vm.model.State
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status

@Service
class VMManager(
    private val kvmService: KVMService,
){
    private val db = HashMap<Int, KVM>()
    fun getKvmList(state : State): Set<KVM>{
        return kvmService.getList(state)
    }

    fun createKvm(): CreateResponse<Int> =
        try {
            val id = kvmService.create()
            CreateResponse(id, Status.IN_PROGRESS)
        } catch (e: Exception) {
            CreateResponse(null, Status.DECLINED, e.message)
        }

    fun getKvmById(id : Int) : KVM = kvmService.getById(id)
}