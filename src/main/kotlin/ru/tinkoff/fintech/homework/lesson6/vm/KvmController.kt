package ru.tinkoff.fintech.homework.lesson6.vm

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.Vm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateVmRequest
import ru.tinkoff.fintech.homework.lesson6.vm.service.KvmService

@RestController
@RequestMapping("/vm")
class KvmController(private val kvmService: KvmService) {
    @GetMapping("/kvm_list")
    fun getKvmListWithParams(
        @RequestParam osType: String,
        @RequestParam rows: Int,
        @RequestParam page: Int
    ): List<Vm> =
        kvmService.getKvmList(osType, rows, page)

    @PostMapping("/create")
    fun createKvm(@RequestBody request: CreateVmRequest): CreateResponse<Int> {
        return kvmService.create(request.imgId, request.configId, request.osType)
    }

    @GetMapping("/kvm/{id}")
    fun getKvmById(@PathVariable id: Int): CreateResponse<Vm> =
        kvmService.getKvmById(id)
}