package ru.tinkoff.fintech.homework.lesson6.vm

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.CreateVmRequest
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.service.KVMService

@RestController
@RequestMapping("/vm")
class KvmController(private val kvmService: KVMService) {
    @GetMapping("/kvm_list")
    fun getKvmListWithParams(
        @RequestParam osType: String,
        @RequestParam rows: Int,
        @RequestParam page: Int
    ): List<Kvm> =
        kvmService.getKvmList(osType, rows, page)

    @PostMapping("/create")
    fun createKvm(@RequestBody request: CreateVmRequest): CreateResponse<Int> {
        return kvmService.create(request.imgId, request.configId)
    }

    @GetMapping("/kvm/{id}")
    fun getKvmById(@PathVariable id: Int): CreateResponse<Kvm> =
        kvmService.getKvmById(id)
}