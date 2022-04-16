package ru.tinkoff.fintech.homework.lesson6.vm.model.external

data class CreateResponse<T>(
    val item: T? = null,
    val status: VmManagerStatus?,
    val comment: String? = null
)