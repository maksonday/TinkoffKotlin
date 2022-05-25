package whois.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.mail.SimpleMailMessage

@Profile("whois")
@Configuration
open class TemplateConfig {
    @Bean
    open fun exampleNewsletterTemplate(): SimpleMailMessage {
        val template = SimpleMailMessage()
        template.subject = "Domain prolongation"
        template.text = """
                Hello %s, 
                
                Your domain %s will expire on %s.
                Please prolong it.
            """.trimIndent()
        return template
    }
}