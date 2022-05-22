package ru.tinkoff.fintech.homework.lesson10.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.EnableJms
import org.apache.activemq.command.ActiveMQQueue
import org.springframework.scheduling.annotation.EnableScheduling

@Profile("mq")
@EnableJms
@EnableScheduling
@Configuration
open class ActiveMqConfig {
    @Bean
    open fun queue() = ActiveMQQueue(MESSAGE_QUEUE)

    companion object {
        const val MESSAGE_QUEUE = "message-queue"
    }
}