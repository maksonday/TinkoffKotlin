package ru.tinkoff.fintech.homework.lesson6.vm.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.configuration.ControllerExceptionHandler
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmManagerStatus

@Service
class KvmService(
    private val kvmManager: KvmManager,
) {
    private val exceptionHandler = ControllerExceptionHandler()

    fun getKvmList(osType: String, rows: Int, page: Int): List<Vm> {
        return kvmManager.getList(osType, rows, page)
    }

    fun create(imgId: Int, configId: Int, osType: String): CreateResponse<Int> =
        try {
            val id = kvmManager.create(imgId, configId, osType)
            CreateResponse(id, VmManagerStatus.IN_PROGRESS)
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
            CreateResponse(null, VmManagerStatus.DECLINED, e.message)
        }

    fun getKvmById(id: Int): CreateResponse<Vm> = try {
        CreateResponse(kvmManager.getById(id), VmManagerStatus.READY)
    } catch (e: IllegalArgumentException) {
        exceptionHandler.handleIllegalArgumentException(e)
        CreateResponse(null, null, e.message)
    }
}