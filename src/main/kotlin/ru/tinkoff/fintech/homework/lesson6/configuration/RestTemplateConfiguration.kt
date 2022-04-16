package ru.tinkoff.fintech.homework.lesson6.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
open class RestTemplateConfiguration(
    @Value("\${connect.timeout.in.seconds}") private val connectTimeout: String,
    @Value("\${read.timeout.in.seconds}") private val readTimeout: String
) {

    @Bean
    open fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(connectTimeout.toLong()))
        .setReadTimeout(Duration.ofSeconds(readTimeout.toLong()))
        .build()
}