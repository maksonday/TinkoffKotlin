package ru.tinkoff.fintech.homework.lesson10.configuration

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.tinkoff.fintech.homework.lesson10.db.EventProcessor

@Component
class Producer(private val eventProcessor: EventProcessor) {
    @Scheduled(cron = "\${cron.expression}")
    fun startProducer() {
        eventProcessor.processNewEvents()
    }
}