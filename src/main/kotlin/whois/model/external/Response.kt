package whois.model.external

data class Response<T>(
    val item: T?,
    val comment: String?
)