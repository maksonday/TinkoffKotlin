package ru.tinkoff.fintech.homework.lesson6.vm.model

import javax.persistence.*

@Entity
@Table(name = "images")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int?,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "type", nullable = false)
    val type: String,
    @Column(name = "diskSizeRequirements", nullable = false)
    val diskSizeRequirements: Int,
    @Column(name = "ramRequirements", nullable = false)
    val ramRequirements: Int,
)