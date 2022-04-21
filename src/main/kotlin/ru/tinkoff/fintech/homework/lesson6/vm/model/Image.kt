package ru.tinkoff.fintech.homework.lesson6.vm.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "images")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long?,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "type", nullable = false)
    val type: String,
    @Column(name = "diskSizeRequirements", nullable = false)
    val diskSizeRequirements: Int,
    @Column(name = "ramRequirements", nullable = false)
    val ramRequirements: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Image

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , type = $type , diskSizeRequirements = $diskSizeRequirements , ramRequirements = $ramRequirements )"
    }
}