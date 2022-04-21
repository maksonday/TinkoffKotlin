package ru.tinkoff.fintech.homework.lesson6.vm.model

import org.hibernate.Hibernate
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
    val id: Long?,
    @Column(name = "imageId", nullable = false)
    override var imageId: Long?,
    @Column(name = "configId", nullable = false)
    override val configId: Long?,
    @Column(name = "osType", nullable = false)
    override val osType: String = "Linux",
    @Column(name = "state", nullable = false)
    override val state: VmState,
    @Column(name = "status", nullable = false)
    override val vmStatus: VmStatus
) : Vm {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Kvm

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , type = $type , imageId = $imageId , configId = $configId , osType = $osType )"
    }
}
