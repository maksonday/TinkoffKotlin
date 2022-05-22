package ru.tinkoff.fintech.homework.lesson10.model.notifications

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