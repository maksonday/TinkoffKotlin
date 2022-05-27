package ru.tinkoff.fintech.homework.lesson10.components

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.tinkoff.fintech.homework.lesson10.model.service.EventProcessor

@Component
class Producer(private val eventProcessor: EventProcessor) {
    @Scheduled(cron = "\${cron.expression}")
    fun startProducer() {
        eventProcessor.processNewEvents()
    }
}