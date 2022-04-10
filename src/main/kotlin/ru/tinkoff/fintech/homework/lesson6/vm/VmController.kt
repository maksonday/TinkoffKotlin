package ru.tinkoff.fintech.homework.lesson6.vm

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.vm.model.CreateVmRequest
import ru.tinkoff.fintech.homework.lesson6.vm.model.KVM
import ru.tinkoff.fintech.homework.lesson6.vm.model.State
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.CreateResponse
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.Status
import ru.tinkoff.fintech.homework.lesson6.vm.service.VMManager

@RestController
@RequestMapping("/vm")
class VmController(private val vmManager : VMManager) {
    @GetMapping("/kvm_list")
    fun getKvmList(@RequestParam state : State): Set<KVM> =
        vmManager.getKvmList(state)

    @PostMapping("/create")
    fun createKvm(@RequestBody request: CreateVmRequest): CreateResponse<Int> {
        return if (request.type == "kvm") vmManager.createKvm()
        else CreateResponse(null, Status.DECLINED, "Incorrect request")
    }

    @GetMapping("/kvm/{id}")
    fun getKvmById(@PathVariable id: Int): KVM =
        vmManager.getKvmById(id)
}