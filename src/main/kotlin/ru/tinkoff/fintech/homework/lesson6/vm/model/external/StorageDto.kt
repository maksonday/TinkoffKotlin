package ru.tinkoff.fintech.homework.lesson6.vm.model.external

import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image

data class GetInfoResponse(val id: Int, val config : Config, val img : Image)
