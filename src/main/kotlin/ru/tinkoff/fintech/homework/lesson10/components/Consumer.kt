package ru.tinkoff.fintech.homework.lesson10.components

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import ru.tinkoff.fintech.homework.lesson10.model.Event
import ru.tinkoff.fintech.homework.lesson10.model.service.EventProcessor

@Component
class Consumer(private val eventProcessor: EventProcessor) {
    companion object {
        const val MESSAGE_QUEUE = "message-queue"
    }

    @JmsListener(destination = MESSAGE_QUEUE)
    fun listener(event: Event) {
        try {
            eventProcessor.processEvent(event)
        } catch (e: Exception) {
            eventProcessor.handleFailedEvent(event, e.message)
        }
    }
}