package ru.tinkoff.fintech.homework.lesson10.model.notifications

class SmsNotification : Notification {
    override fun sendNotification(message: String) {
        try{
            TODO("Not yet implemented")
        }
        catch (e : Exception){
            throw Exception(e.message)
        }
    }
}