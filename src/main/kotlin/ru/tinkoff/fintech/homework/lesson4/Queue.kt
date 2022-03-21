package ru.tinkoff.fintech.homework.lesson4

import java.lang.Exception

class Queue<E>() {
    private var queue = mutableListOf<E>()
    private var size = 0

    fun offer(obj: E) : Boolean {
        if (queue.add(obj)){
            size++
            return true
        }
        return false
    }

    fun remove(): E {
        if (isEmpty()) throw Exception("NoSuchElementException")
        else {
            val temp = queue[0]
            queue.removeFirst()
            size--
            return temp
        }
    }

    fun element(): E {
        if (isEmpty()) throw Exception("NoSuchElementException")
        else return queue[0]
    }

    fun peek(): E? {
        return if (isEmpty()) null
        else queue[0]
    }

    fun poll(): E?{
        return if (isEmpty()) null
        else {
            val temp = queue[0]
            queue.removeFirst()
            size--
            temp
        }
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Queue<*>

        if (queue != other.queue) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = queue.hashCode()
        result = 31 * result + size
        return result
    }
}