package whois.components

import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import whois.service.EventProcessor

@Profile("whois")
@Component
class Producer(private val eventProcessor: EventProcessor) {
    @Scheduled(cron = "\${cron.expression}")
    fun startProducer() {
        eventProcessor.checkProlongations()
        eventProcessor.processNewEvents()
    }
}