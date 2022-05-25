package whois.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Profile

@Profile("whois")
@ConstructorBinding
@ConfigurationProperties(prefix = "mail-sender")
class MailSenderProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val protocol: String,
    val auth: Boolean,
    val starttlsEnable: Boolean,
    val debug: Boolean
)