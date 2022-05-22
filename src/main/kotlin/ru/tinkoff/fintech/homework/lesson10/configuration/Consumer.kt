package ru.tinkoff.fintech.homework.lesson10.configuration

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import ru.tinkoff.fintech.homework.lesson10.db.EventProcessor
import ru.tinkoff.fintech.homework.lesson10.model.Event
import ru.tinkoff.fintech.homework.lesson10.model.EventStatus
import ru.tinkoff.fintech.homework.lesson10.model.EventType
import ru.tinkoff.fintech.homework.lesson10.model.notifications.EmailNotification
import ru.tinkoff.fintech.homework.lesson10.model.notifications.PushNotification
import ru.tinkoff.fintech.homework.lesson10.model.notifications.SmsNotification

@Component
class Consumer(private val eventProcessor: EventProcessor) {
    companion object {
        const val MESSAGE_QUEUE = "message-queue"
    }

    @JmsListener(destination = MESSAGE_QUEUE)
    fun listener(event : Event){
        try {
            when (event.type) {
                EventType.PUSH -> PushNotification().sendNotification(event.body)
                EventType.EMAIL -> EmailNotification().sendNotification(event.body)
                EventType.SMS -> SmsNotification().sendNotification(event.body)
            }
            event.status = EventStatus.DONE
            eventProcessor.updateEvent(event)
        }
        catch (e : Exception){
            event.status = EventStatus.ERROR
            eventProcessor.updateEvent(event)
            eventProcessor.logError(e)
        }
    }
}