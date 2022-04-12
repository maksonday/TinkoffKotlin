package ru.tinkoff.fintech.homework.lesson6.vm.model.external

import ru.tinkoff.fintech.homework.lesson6.vm.model.Config
import ru.tinkoff.fintech.homework.lesson6.vm.model.Image
import ru.tinkoff.fintech.homework.lesson6.vm.model.Kvm

data class GetImageResponse(val img: Image)

data class GetConfigResponse(val config: Config)

data class CreateVMRequest(val kvm: Kvm)