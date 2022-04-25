package ru.tinkoff.fintech.homework.lesson8

import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(count: Int) : Executor {
    companion object{
        const val TIMEOUT = 10000L
    }
    private var queue = LinkedBlockingQueue<Runnable>()
    private var threadList = mutableListOf<WorkerThread>()

    init {
        require(count <= Runtime.getRuntime().availableProcessors()) {
            throw IllegalArgumentException(
                "Too much threads. Amount of threads in pool must be equal or less than ${
                    Runtime.getRuntime().availableProcessors()
                }."
            )
        }
        require(count > 0) {
            throw IllegalArgumentException("Amount of threads must be a positive number")
        }
        for (i in 0 until count) {
            val thread = WorkerThread(queue)
            threadList.add(thread)
            thread.start()
        }
    }

    override fun execute(command: Runnable) {
        synchronized(queue) {
            queue.add(command)
            (queue as Object).notify()
        }
    }

    fun shutdown(): Boolean {
        for (thread in threadList) {
            thread.interrupt()
            val time = System.currentTimeMillis()
            try {
                thread.join(TIMEOUT)
            } catch (e: Exception) {
                println(e.message)
                return false
            }
            if (System.currentTimeMillis() - time > TIMEOUT){
                return false
            }
        }
        return true
    }
}