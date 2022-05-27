package ru.tinkoff.fintech.homework.lesson10.model.notifications

import org.slf4j.LoggerFactory

interface Notification {
    abstract fun sendNotification(message: String)
}