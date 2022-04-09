package ru.tinkoff.fintech.homework.lesson6.vm.model.external

import ru.tinkoff.fintech.homework.lesson6.vm.model.VM

data class Create(
    val id: Int,
    val vm: VM,
    val status: Status = Status.IN_PROGRESS
)

data class CreateResponse<T>(
    val item: T? = null,
    val status: Status,
    val comment: String? = null
)

enum class Status { IN_PROGRESS, READY, DECLINED }