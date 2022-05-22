package ru.tinkoff.fintech.homework.lesson10.model.service

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson10.db.EventRepository
import ru.tinkoff.fintech.homework.lesson10.model.Event
import ru.tinkoff.fintech.homework.lesson10.model.EventStatus
import ru.tinkoff.fintech.homework.lesson10.model.EventType
import ru.tinkoff.fintech.homework.lesson10.model.notifications.EmailNotification
import ru.tinkoff.fintech.homework.lesson10.model.notifications.PushNotification
import ru.tinkoff.fintech.homework.lesson10.model.notifications.SmsNotification
import javax.jms.Queue

@Profile("mq")
@Service
class EventProcessor(private val repo : EventRepository, private val jmsTemplate: JmsTemplate, private val queue : Queue) {
    private val log = LoggerFactory.getLogger(javaClass)

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
            EventType.PUSH -> {
                PushNotification().sendNotification(event.body)
            }
            EventType.EMAIL -> {
                EmailNotification().sendNotification(event.body)
            }
            EventType.SMS -> {
                SmsNotification().sendNotification(event.body)
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