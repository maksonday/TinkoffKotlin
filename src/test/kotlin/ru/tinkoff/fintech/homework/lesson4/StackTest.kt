package ru.tinkoff.fintech.homework.lesson4

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class StackTest{
    @Test
    fun `push всегда должен возвращать true и добавить элемент в стек`() {
        val stack = Stack<Any>()

        val check = stack.push(10)

        assertAll(
            {
                assertEquals(true, check)
                assertEquals(false, stack.isEmpty())
            }
        )
    }

    @Test
    fun `pop должен выбрасывать исключение, если стек пустой`() {
        val stack = Stack<Any>()

        val exception : Exception = assertThrows(Exception::class.java) {
            stack.pop()
        }

        assertEquals("NoSuchElementException", exception.message)
    }

    @Test
    fun `pop должен вернуть элемент из верха стека и удалить его из стека, если стек не пустой`() {
        val stack = Stack<Any>()
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
    fun `peek должен вернуть элемент с верха стека, не удаляя его, если стек не пустой`(){
        val stack = Stack<Any>()
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
        val stack = Stack<Any>()

        val exception : Exception = assertThrows(Exception::class.java) {
            stack.peek()
        }

        assertEquals("NoSuchElementException", exception.message)
    }

    @Test
    fun `isEmpty должен возвращать true, если стек пустой`(){
        val stack = Stack<Any>()

        val check = stack.isEmpty()

        assertEquals(true, check)
    }

    @Test
    fun `isEmpty должен возвращать false, если стек не пустой`(){
        val stack = Stack<Any>()
        stack.push(10)

        val check = stack.isEmpty()

        assertEquals(false, check)
    }
}