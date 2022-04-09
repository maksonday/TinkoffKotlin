package ru.tinkoff.fintech.homework.lesson6.vm.model

interface VM {
    val type : String
    val id : Int
    val image : Image
    val config : Config
    val osType: String
    val state : State
    val status : Status
}

fun VM.toMap(): Map<String, Any>{
    return mapOf(
        "type" to this.type,
        "id" to this.id,
        "image" to this.image.toString(),
        "config" to this.config.toString(),
        "osType" to this.osType,
        "state" to this.state.toString(),
        "status" to this.status.toString()
    )
}

enum class Status { DISK_DETACHED, DISK_ATTACHED }

enum class State { OFF, RUNNING, BOOT, SHUTTING_DOWN }