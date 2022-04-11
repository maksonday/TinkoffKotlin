package ru.tinkoff.fintech.homework.lesson6.vm.model

data class CreateVmRequest(
    val type: String,
    val imgId: Int,
    val configId: Int
)