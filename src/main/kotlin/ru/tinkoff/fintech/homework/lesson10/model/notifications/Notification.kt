package ru.tinkoff.fintech.homework.lesson10.model.notifications

interface Notification {
    abstract fun sendNotification(message: String)
}