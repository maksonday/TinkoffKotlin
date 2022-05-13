package ru.tinkoff.fintech.homework.lesson6.vm.model

import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmState
import ru.tinkoff.fintech.homework.lesson6.vm.model.external.VmStatus
import javax.persistence.*

@Entity
@Table(name = "vm")
data class Kvm(
    @Column(name = "type", nullable = false)
    override val type: String = "kvm",
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int?,
    @Column(name = "imageId", nullable = false)
    override var imageId: Int?,
    @Column(name = "configId", nullable = false)
    override val configId: Int?,
    @Column(name = "osType", nullable = false)
    override val osType: String = "Linux",
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    override val state: VmState,
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    override val vmStatus: VmStatus
) : Vm
