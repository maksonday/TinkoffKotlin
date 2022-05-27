package ru.tinkoff.fintech.homework.lesson10.model.notifications

class PushNotification : Notification {
    override fun sendNotification(message: String) {
        try{
            println("PUSH: $message")
        }
        catch (e : Exception){
            throw Exception(e.message)
        }
    }
}