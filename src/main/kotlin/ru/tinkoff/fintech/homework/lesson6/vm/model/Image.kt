package ru.tinkoff.fintech.homework.lesson6.vm.model

import javax.persistence.*

@Entity
@Table(name = "images")
data class Image(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int?,
    val name: String,
    val type: String,
    val diskSizeRequirements: Int,
    val ramRequirements: Int,
)