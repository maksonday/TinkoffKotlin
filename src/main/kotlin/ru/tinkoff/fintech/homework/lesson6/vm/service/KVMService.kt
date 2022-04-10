package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM
import ru.tinkoff.fintech.homework.lesson6.vm.model.State
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Create
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KVMListClient

@Service
class KVMService (private val kvmListClient: KVMListClient) {
    fun getList(state : State): Set <KVM> = kvmListClient.getList(state)

    fun getById(id : Int) : KVM{
        val kvm = kvmListClient.getById(id)
        return requireNotNull(kvm) { "Не существует KVM с данным id = $id" }
    }

    fun create(type : String, id : Int, image : Image, config : Config): Int? {
        return kvmListClient.create(id, type, image, config)
    }
}