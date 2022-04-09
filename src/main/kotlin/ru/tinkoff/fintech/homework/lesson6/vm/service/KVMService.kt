package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.ImgServiceClient
import ru.tinkoff.fintech.homework.lesson6.vm.service.client.KVMListClient

@Service
class KVMService (private val kvmListClient: KVMListClient, private val imgServiceClient: ImgServiceClient) {
    fun getList() : Set <KVM> = kvmListClient.getList()

    fun getById(id : Int) : KVM{
        val kvm = kvmListClient.getById(id)
        return requireNotNull(kvm) { "Не существует KVM с данным id = $id" }
    }

    fun detachDisk(id : Int){

    }

    fun attachDisk(id : Int){

    }

    fun changeImg(vmId : Int, imgId : Int){
        powerOff(vmId)
        detachDisk(vmId)
        val kvm : KVM? = kvmListClient.getById(vmId)
        if (kvm != null) {
            val img : Image? = imgServiceClient.getById(imgId)
            if (img != null) kvm.image = img
        }
        attachDisk(vmId)
        boot(vmId)
    }

    fun powerOff(id : Int){

    }

    fun boot(id : Int){

    }

    fun create(): Int? {

    }
}