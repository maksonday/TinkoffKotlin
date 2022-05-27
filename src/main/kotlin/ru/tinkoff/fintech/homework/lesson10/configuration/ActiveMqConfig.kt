package ru.tinkoff.fintech.homework.lesson10.configuration

import org.apache.activemq.command.ActiveMQQueue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.config.JmsListenerContainerFactory
import org.springframework.jms.support.converter.MappingJackson2MessageConverter
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.jms.support.converter.MessageType
import org.springframework.scheduling.annotation.EnableScheduling


@Profile("mq")
@EnableJms
@EnableScheduling
@Configuration
open class ActiveMqConfig {
    @Bean
    open fun queue() = ActiveMQQueue(MESSAGE_QUEUE)

    @Bean
    open fun queueListenerFactory(): JmsListenerContainerFactory<*>? {
        val factory = DefaultJmsListenerContainerFactory()
        factory.setMessageConverter(messageConverter())
        return factory
    }

    @Bean
    open fun messageConverter(): MessageConverter {
        val converter = MappingJackson2MessageConverter()
        converter.setTargetType(MessageType.TEXT)
        converter.setTypeIdPropertyName("_type")
        return converter
    }

    companion object {
        const val MESSAGE_QUEUE = "message-queue"
    }
}