package ru.tinkoff.fintech.homework.lesson6.vm.model

import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus
import javax.persistence.*

@Entity
@Table(name = "vm")
data class Kvm(
    @Column
    override val type: String = "kvm",
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int?,
    @Column
    override var imageId: Int?,
    @Column
    override val configId: Int?,
    @Column
    override val osType: String = "Linux",
    @Column
    override val state: VmState,
    @Column
    override val vmStatus: VmStatus
) : Vm
