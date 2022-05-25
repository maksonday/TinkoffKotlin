package whois.service.external

import org.springframework.context.annotation.Profile
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Profile("whois")
@Service
class EmailSenderService(
    private val emailSender: JavaMailSender,
    private val template: SimpleMailMessage
) {
    fun sendEmail(
        subject: String,
        text: String,
        targetEmail: String
    ) {
        val message = SimpleMailMessage()
        message.subject = subject
        message.text = text
        message.setTo(targetEmail)

        emailSender.send(message)
    }

    fun sendEmailUsingTemplate(
        name: String,
        domain: String,
        expire: String,
        targetEmail: String
    ) {
        val message = SimpleMailMessage(template)
        val text = template.text
        message.text = text!!.format(name, domain, expire)
        message.setTo(targetEmail)
        emailSender.send(message)
    }
}