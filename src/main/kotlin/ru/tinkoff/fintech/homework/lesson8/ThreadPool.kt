package ru.tinkoff.fintech.homework.lesson8

import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue

class ThreadPool(count: Int) : Executor {
    companion object {
        const val SHUTDOWN_TIMEOUT = 60000L
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
            threadList.add(WorkerThread(queue))
        }
        for (thread in threadList) {
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
        }

        val startTime = System.currentTimeMillis()
        var failingThread: String? = null

        while (true) {
            var result = true
            for (thread in threadList) {
                if (thread.isAlive) {
                    result = false
                    failingThread = thread.name
                    break
                }
            }
            if (result) {
                break
            }
            if (System.currentTimeMillis() - startTime > SHUTDOWN_TIMEOUT) {
                if (failingThread != null) throw RuntimeException("An error occurred: can't stop thread ${failingThread}")
                else throw RuntimeException("Something went wrong during shutdown")
            }
        }
        return true
    }
}