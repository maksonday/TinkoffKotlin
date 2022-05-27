package ru.tinkoff.fintech.homework.lesson10.model.notifications

class EmailNotification : Notification {
    override fun sendNotification(message: String) {
        try{
            println("EMAIL: $message")
        }
        catch (e : Exception){
            throw Exception(e.message)
        }
    }
}