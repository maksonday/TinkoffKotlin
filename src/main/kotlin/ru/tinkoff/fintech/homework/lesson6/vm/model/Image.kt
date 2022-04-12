package ru.tinkoff.fintech.homework.lesson6.vm.model

data class Image(
    val name: String,
    val type: String,
    val diskSizeRequirements: Int,
    val ramRequirements: Int,
)