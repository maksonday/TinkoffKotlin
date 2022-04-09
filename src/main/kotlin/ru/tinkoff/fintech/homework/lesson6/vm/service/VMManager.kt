package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Create
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status

@Service
class VMManager (
    private val kvmService : KVMService,
    private val hypervService : HyperVService,
){
    fun getKvmList() : Set<KVM>{
        return kvmService.getList().toSet()
    }

    fun createKvm(): CreateResponse<Int> =
        try {
            val id = kvmService.create()
            CreateResponse(id, Status.IN_PROGRESS)
        } catch (e: Exception) {
            CreateResponse(null, Status.DECLINED, e.message)
        }

    fun getKvmById(id : Int) : Map<String, Any> = kvmService.getById(id).toMap()
}