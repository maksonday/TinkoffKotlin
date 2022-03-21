package ru.tinkoff.fintech.homework.lesson4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class QueueTest {
    @Test
    fun `offer всегда должен возвращать true и добавить элемент в очередь`() {
        val queue = Queue<Any>()

        val check = queue.offer(10)

        assertAll(
            {
                assertEquals(true, check)
                assertEquals(false, queue.isEmpty())
            }
        )
    }

    @Test
    fun `remove должен выбрасывать исключение, если очередь пустая`() {
        val queue = Queue<Any>()

        val exception : Exception = assertThrows(Exception::class.java){
            queue.remove()
        }

        assertEquals("NoSuchElementException", exception.message)
    }

    @Test
    fun `remove должен вернуть элемент из головы очереди и удалить его из очереди, если очередь не пустая`() {
        val queue = Queue<Any>()
        queue.offer(10)

        val element = queue.remove()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он удалился при помощи вызова isEmpty",
            {
                assertEquals(10, element)
                assertEquals(true, queue.isEmpty())
            }
        )

    }

    @Test
    fun `element должен выбрасывать исключение, если очередь пустая`() {
        val queue = Queue<Any>()

        val exception : Exception = assertThrows(Exception::class.java){
            queue.element()
        }

        assertEquals("NoSuchElementException", exception.message)
    }

    @Test
    fun `element должен вернуть элемент из головы очереди, не удаляя его, если очередь не пустая`() {
        val queue = Queue<Any>()
        queue.offer(10)

        val element = queue.element()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он не удалился при помощи вызова isEmpty",
            {
                assertEquals(10, element)
                assertEquals(false, queue.isEmpty())
            }
        )
    }

    @Test
    fun `peek должен вернуть null, если очередь пустая`() {
        val queue = Queue<Any>()

        val check = queue.peek()

        assertEquals(null, check)
    }

    @Test
    fun `peek должен вернуть элемент из головы очереди, не удаляя его, если очередь не пустая`() {
        val queue = Queue<Any>()
        queue.offer(10)

        val peek = queue.element()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он не удалился при помощи вызова isEmpty",
            {
                assertEquals(10, peek)
                assertEquals(false, queue.isEmpty())
            }
        )
    }

    @Test
    fun `poll должен вернуть null, если очередь пустая`() {
        val queue = Queue<Any>()

        val check = queue.poll()

        assertEquals(null, check)
    }

    @Test
    fun `poll должен вернуть элемент из головы очереди и удалить его, если очередь не пустая`() {
        val queue = Queue<Any>()
        queue.offer(10)

        val peek = queue.poll()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он удалился при помощи вызова isEmpty",
            {
                assertEquals(10, peek)
                assertEquals(true, queue.isEmpty())
            }
        )
    }

    @Test
    fun `isEmpty должен возвращать true, если очередь пустая`(){
        val queue = Queue<Any>()

        val check = queue.isEmpty()

        assertEquals(true, check)
    }

    @Test
    fun `isEmpty должен возвращать false, если очередь не пустая`(){
        val queue = Queue<Any>()
        queue.offer(10)

        val check = queue.isEmpty()

        assertEquals(false, check)
    }

}