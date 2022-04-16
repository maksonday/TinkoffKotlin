package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.configuration.ControllerExceptionHandler
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmManagerStatus

@Service
class KvmService(
    private val vmManager: VmManager,
) {
    private val exceptionHandler = ControllerExceptionHandler()

    fun getKvmList(osType: String, rows: Int, page: Int): List<Kvm> {
        return vmManager.getList(osType, rows, page)
    }

    fun create(imgId: Int, configId: Int): CreateResponse<Int> =
        try {
            val id = vmManager.create(imgId, configId)
            CreateResponse(id, VmManagerStatus.IN_PROGRESS)
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
            CreateResponse(null, VmManagerStatus.DECLINED, e.message)
        }

    fun getKvmById(id: Int): CreateResponse<Kvm> = try {
        CreateResponse(vmManager.getById(id), VmManagerStatus.READY)
    } catch (e: IllegalArgumentException) {
        exceptionHandler.handleIllegalArgumentException(e)
        CreateResponse(null, null, e.message)
    }
}