package ru.tinkoff.fintech.homework.lesson4

class Queue<E>(private val capacity: Int) {
    init {
        require(capacity >= 0)
    }

    private var queue = mutableListOf<E>()
    private var size = 0

    fun offer(obj: E): Boolean {
        if (size + 1 > capacity) return false
        else {
            queue.add(obj)
            size++
            return true
        }
    }

    fun remove(): E {
        if (isEmpty()) throw NoSuchElementException("Queue is empty")
        else {
            val temp = queue[0]
            queue.removeFirst()
            size--
            return temp
        }
    }

    fun element(): E {
        if (isEmpty()) throw NoSuchElementException("Queue is empty")
        else return queue[0]
    }

    fun peek(): E? {
        return if (isEmpty()) null
        else queue[0]
    }

    fun poll(): E? {
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