package ru.tinkoff.fintech.homework.lesson6.vm.model

import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

interface Vm {
    val type: String
    val id: Int?
    val image: Image
    val config: Config
    val osType: String
    val state: VmState
    val vmStatus: VmStatus
}