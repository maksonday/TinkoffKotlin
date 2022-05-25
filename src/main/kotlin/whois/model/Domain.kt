package whois.model

data class Domain(
    val id: Int? = null,

    val name: String? = null,

    val ip: String? = null,

    var created: String? = null,

    var fetched: String? = null,

    var paidTill: String? = null,

    var freeDate: String? = null,

    val registrar: String? = null,

    val admin: String? = null,

    val source: String? = null
)