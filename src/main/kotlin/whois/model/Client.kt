package whois.model

data class Client(
    val id: Int? = null,

    var name: String? = null,

    val address: String? = null,

    var phone: String? = null,

    var fax: String? = null,

    var email: String? = null,

    var domainId: Int? = null,
)