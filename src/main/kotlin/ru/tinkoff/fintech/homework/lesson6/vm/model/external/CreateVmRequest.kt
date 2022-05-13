package ru.tinkoff.fintech.homework.lesson6.vm.model.external

data class CreateVmRequest(
    val type: String,
    val imgId: Int,
    val configId: Int,
    val osType : String
)