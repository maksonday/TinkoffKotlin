package whois.model.external

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import whois.service.external.EmailSenderService

@Profile("whois")
@Service
class EmailNotification(private val emailSenderService : EmailSenderService) {
    fun sendNotification(message: String) {
        try{
            val (domain, name, email, expire) = message.split(";")
            emailSenderService.sendEmailUsingTemplate(
                name = name,
                targetEmail = email,
                domain = domain,
                expire = expire
            )
        }
        catch (e : Exception){
            throw Exception(e.message)
        }
    }
}