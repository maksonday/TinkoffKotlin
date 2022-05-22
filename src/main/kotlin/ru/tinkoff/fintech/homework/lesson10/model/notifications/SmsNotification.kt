package ru.tinkoff.fintech.homework.lesson10.model.notifications

import org.slf4j.LoggerFactory
import ru.tinkoff.fintech.homework.lesson10.model.service.EventProcessor

class SmsNotification : Notification {
    override fun sendNotification(message: String) {
        try{
            println("SMS: $message")
        }
        catch (e : Exception){
            throw Exception(e.message)
        }
    }
}