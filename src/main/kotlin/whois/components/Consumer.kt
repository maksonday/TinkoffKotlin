package whois.components

import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import whois.model.Event
import whois.service.EventProcessor

@Profile("whois")
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