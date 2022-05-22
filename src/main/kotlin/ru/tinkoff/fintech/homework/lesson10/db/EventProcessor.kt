package ru.tinkoff.fintech.homework.lesson10.db

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson10.model.Event
import ru.tinkoff.fintech.homework.lesson10.model.EventStatus
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

    fun updateEvent(event : Event){
        try {
            repo.save(event)
        }
        catch (e : Exception){
            log.warn(e.message)
        }
    }

    fun logError(e : Exception){
        log.warn(e.message)
    }
}