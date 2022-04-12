package ru.tinkoff.fintech.homework.lesson6.vm.model.external

data class CreateResponse<T>(
    val item: T? = null,
    val status: Status?,
    val comment: String? = null
)

enum class Status { IN_PROGRESS, READY, DECLINED }