package ru.tinkoff.fintech.homework.lesson9.domain.model

import javax.persistence.*

@Entity
@Table(name = "domains")
data class Domain(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int? = null,

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "created")
    var created: String? = null,

    @Column(name = "fetched")
    var fetched: String? = null,

    @Column(name = "paidTill")
    var paidTill: String? = null,

    @Column(name = "freeDate")
    var freeDate: String? = null,
)