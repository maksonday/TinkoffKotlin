package whois.service

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import whois.db.DomainDao
import whois.db.EventRepository
import whois.model.Event
import whois.model.EventStatus
import whois.model.EventType
import whois.model.external.EmailNotification
import java.lang.Thread.sleep
import javax.jms.Queue

@Profile("whois")
@Service
class EventProcessor(private val repo : EventRepository, private val jmsTemplate: JmsTemplate, private val queue : Queue, private val domainDao: DomainDao, private val emailSenderService: EmailNotification) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun checkProlongations(){
        val domainsToProlong = domainDao.getDomainsToProlong()
        if (domainsToProlong != null) {
            for (i in domainsToProlong){
                val tmpList = mutableListOf<String>()
                tmpList.add(i.first)
                i.second.forEach{
                    tmpList.add(it)
                }
                repo.save(Event(null, EventType.EMAIL, EventStatus.NEW, tmpList.joinToString(separator = ";")))
            }
        }
    }

    fun processNewEvents(){
        val eventList = repo.findByStatus(EventStatus.NEW)
        for (event in eventList){
            try {
                jmsTemplate.convertAndSend(queue, event)
                event.status = EventStatus.IN_PROCESS
            }
            catch (e : Exception){
                event.status = EventStatus.ERROR
            }
            updateEvent(event)
        }
    }

    fun processEvent(event: Event){
        when (event.type) {
            EventType.EMAIL -> {
                emailSenderService.sendNotification(event.body)
            }
        }
        event.status = EventStatus.DONE
        updateEvent(event)
    }

    fun handleFailedEvent(event: Event, message: String?){
        event.status = EventStatus.ERROR
        updateEvent(event)
        if (message != null) log("${event.type}: $message")
        else log("${event.type}: something went wrong")
    }

    private fun updateEvent(event : Event){
        try {
            repo.save(event)
        }
        catch (e : Exception){
            log.warn(e.message)
        }
    }

    private fun log(message: String){
        log.warn(message)
    }
}