package whois.model

import javax.persistence.*

@Entity
@Table(name = "events")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int?,

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    val type: EventType,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: EventStatus,

    @Column(name = "body")
    val body: String
)