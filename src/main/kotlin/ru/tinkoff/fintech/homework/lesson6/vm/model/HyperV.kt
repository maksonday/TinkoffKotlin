package ru.tinkoff.fintech.homework.lesson6.vm.model

data class HyperV(
    override val type: String,
    override val id: Int,
    override val image: Image,
    override val config : Config,
    override val osType: String = "Windows",
    override val state: State,
    override val status: Status
) : VM