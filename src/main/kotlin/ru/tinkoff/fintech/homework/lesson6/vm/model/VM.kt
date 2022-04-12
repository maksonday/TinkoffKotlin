package ru.tinkoff.fintech.homework.lesson6.vm.model

interface VM {
    val type: String
    val id: Int?
    val image: Image
    val config: Config
    val osType: String
    val state: State
    val vmStatus: VMStatus
}

enum class VMStatus { DISK_DETACHED, DISK_ATTACHED }

enum class State { OFF, RUNNING, BOOT, SHUTTING_DOWN }