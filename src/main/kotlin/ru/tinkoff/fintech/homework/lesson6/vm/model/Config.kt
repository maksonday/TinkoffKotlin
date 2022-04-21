package ru.tinkoff.fintech.homework.lesson6.vm.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "configs")
data class Config(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int?,
    @Column(name = "diskSize", nullable = false)
    val diskSize: Int,
    @Column(name = "cores", nullable = false)
    val cores: Int,
    @Column(name = "sizeRAM", nullable = false)
    val sizeRAM: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Config

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , diskSize = $diskSize , cores = $cores , sizeRAM = $sizeRAM )"
    }
}