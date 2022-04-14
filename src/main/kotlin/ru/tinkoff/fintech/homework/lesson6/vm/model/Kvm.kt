package ru.tinkoff.fintech.homework.lesson6.vm.model

import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus

data class Kvm(
    override val type: String = "kvm",
    override val id: Int?,
    override var image: Image,
    override val config: Config,
    override val osType: String = "Linux",
    override val state: VmState,
    override val vmStatus: VmStatus
) : Vm