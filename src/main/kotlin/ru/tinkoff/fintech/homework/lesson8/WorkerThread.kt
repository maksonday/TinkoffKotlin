package ru.tinkoff.fintech.homework.lesson8

import java.util.concurrent.LinkedBlockingQueue

class WorkerThread(private val queue: LinkedBlockingQueue<Runnable>) : Thread() {
    override fun run() {
        var task: Runnable? = null
        try {
            while (true) {
                synchronized(queue) {
                    if (queue.isEmpty()) (queue as Object).wait()
                    else task = queue.remove()
                }
                if (task != null) {
                    try {
                        task!!.run()
                    }
                    catch (e : Exception){
                        println("An error occurred in ${currentThread().name}: ${e.message}")
                        throw e
                    }
                    task = null
                }
            }
        } catch (e: InterruptedException) {
            if (task != null) {
                try {
                    task!!.run()
                } catch (e: Exception) {
                    println("An error occurred in ${currentThread().name}: ${e.message}")
                    throw e
                }
            }
        }
    }
}