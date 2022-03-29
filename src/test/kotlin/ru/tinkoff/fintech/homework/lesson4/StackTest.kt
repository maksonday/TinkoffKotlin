package ru.tinkoff.fintech.homework.lesson4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StackTest {
    @Test
    fun `если пытаться создать очередь с отрицательным capacity, должно выкидываться исключение`() {
        var stack: Stack<Any>

        val exception = assertThrows(IllegalArgumentException::class.java) {
            stack = Stack<Any>(-1)
        }

        assertEquals("Failed requirement.", exception.message)
    }

    @Test
    fun `push должен возвращать true и добавить элемент в стек, если capacity стека не будет превышена`() {
        val stack = Stack<Any>(1)

        val check = stack.push(10)

        assertAll(
            {
                assertEquals(true, check)
                assertEquals(false, stack.isEmpty())
            }
        )
    }

    @Test
    fun `push должен возвращать false, если мы хотим добавить элемент в стек и при этом capacity будет превышена`() {
        val stack = Stack<Any>(0)

        val check = stack.push(10)

        assertEquals(false, check)
    }

    @Test
    fun `pop должен выбрасывать исключение, если стек пустой`() {
        val stack = Stack<Any>(1)

        val exception = assertThrows(NoSuchElementException::class.java) {
            stack.pop()
        }

        assertEquals("Stack is empty", exception.message)
    }

    @Test
    fun `pop должен вернуть элемент из верха стека и удалить его из стека, если стек не пустой`() {
        val stack = Stack<Any>(1)
        stack.push(10)

        val element = stack.pop()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он удалился при помощи вызова isEmpty",
            {
                assertEquals(10, element)
                assertEquals(true, stack.isEmpty())
            }
        )
    }

    @Test
    fun `peek должен вернуть элемент с верха стека, не удаляя его, если стек не пустой`() {
        val stack = Stack<Any>(1)
        stack.push(10)

        val element = stack.peek()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он удалился при помощи вызова isEmpty",
            {
                assertEquals(10, element)
                assertEquals(false, stack.isEmpty())
            }
        )
    }

    @Test
    fun `peek должен выбрасывать исключение, если стек пустой`() {
        val stack = Stack<Any>(1)

        val exception = assertThrows(NoSuchElementException::class.java) {
            stack.peek()
        }

        assertEquals("Stack is empty", exception.message)
    }

    @Test
    fun `isEmpty должен возвращать true, если стек пустой`() {
        val stack = Stack<Any>(1)

        val check = stack.isEmpty()

        assertEquals(true, check)
    }

    @Test
    fun `isEmpty должен возвращать false, если стек не пустой`() {
        val stack = Stack<Any>(1)
        stack.push(10)

        val check = stack.isEmpty()

        assertEquals(false, check)
    }
}