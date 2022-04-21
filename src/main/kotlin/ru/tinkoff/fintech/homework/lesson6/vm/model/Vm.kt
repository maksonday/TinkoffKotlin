package ru.tinkoff.fintech.homework.lesson6.vm.model

import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

interface Vm {
    val type: String
    val imageId: Int?
    val configId: Int?
    val osType: String
    val state: VmState
    val vmStatus: VmStatus
}