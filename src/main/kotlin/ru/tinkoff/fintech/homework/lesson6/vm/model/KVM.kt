package ru.tinkoff.fintech.homework.lesson6.vm.model

data class KVM(
    override val type: String = "kvm",
    override val id: Int,
    override var image: Image,
    override val config: Config,
    override val osType: String = "Linux",
    override val state: State,
    override val status: VMStatus
) : VM