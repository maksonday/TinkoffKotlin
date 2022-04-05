package ru.tinkoff.fintech.homework.lesson4

class Stack<E>(private val capacity: Int) {
    init {
        require(capacity >= 0)
    }

    private var stack = mutableListOf<E>()
    private var size = 0

    fun push(elem: E): Boolean {
        if (size + 1 > capacity) return false
        else {
            stack.add(elem)
            size++
            return true
        }
    }

    fun pop(): E {
        if (isEmpty()) throw NoSuchElementException("Stack is empty")
        else {
            val temp = stack[size - 1]
            size--
            stack.removeLast()
            return temp
        }
    }

    fun peek(): E {
        if (isEmpty()) throw NoSuchElementException("Stack is empty")
        else {
            return stack[size - 1]
        }
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Stack<*>

        if (stack != other.stack) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stack.hashCode()
        result = 31 * result + size
        return result
    }
}