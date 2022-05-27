package ru.tinkoff.fintech.homework.lesson9.domain.model.external

data class CreateResponse<T>(
    val item: T? = null,
    val comment: String? = null
)