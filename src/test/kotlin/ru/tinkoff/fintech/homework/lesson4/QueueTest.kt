package ru.tinkoff.fintech.homework.lesson4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class QueueTest {
    @Test
    fun `если пытаться создать очередь с отрицательным capacity, должно выкидываться исключение`() {
        var queue: Queue<Any>

        val exception = assertThrows(IllegalArgumentException::class.java) {
            queue = Queue<Any>(-1)
        }

        assertEquals("Failed requirement.", exception.message)
    }

    @Test
    fun `offer должен возвращать true, если мы хотим добавить элемент в очередь и при этом capacity очереди не будет превышена`() {
        val queue = Queue<Any>(1)

        val check = queue.offer(10)

        assertAll(
            {
                assertEquals(true, check)
                assertEquals(false, queue.isEmpty())
            }
        )
    }

    @Test
    fun `offer должен возвращать false, если мы хотим добавить элемент в очередь и при этом capacity будет превышена`() {
        val queue = Queue<Any>(0)

        val check = queue.offer(10)

        assertEquals(false, check)
    }

    @Test
    fun `remove должен выбрасывать исключение, если очередь пустая`() {
        val queue = Queue<Any>(1)

        val exception = assertThrows(NoSuchElementException::class.java) {
            queue.remove()
        }

        assertEquals("Queue is empty", exception.message)
    }

    @Test
    fun `remove должен вернуть элемент из головы очереди и удалить его из очереди, если очередь не пустая`() {
        val queue = Queue<Any>(1)
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
        val queue = Queue<Any>(1)

        val exception = assertThrows(NoSuchElementException::class.java) {
            queue.element()
        }

        assertEquals("Queue is empty", exception.message)
    }

    @Test
    fun `element должен вернуть элемент из головы очереди, не удаляя его, если очередь не пустая`() {
        val queue = Queue<Any>(1)
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
        val queue = Queue<Any>(1)

        val check = queue.peek()

        assertEquals(null, check)
    }

    @Test
    fun `peek должен вернуть элемент из головы очереди, не удаляя его, если очередь не пустая`() {
        val queue = Queue<Any>(1)
        queue.offer(10)

        val peek = queue.peek()

        assertAll("Посмотрим значение первого и единственного элемента и убедимся, что он не удалился при помощи вызова isEmpty",
            {
                assertEquals(10, peek)
                assertEquals(false, queue.isEmpty())
            }
        )
    }

    @Test
    fun `poll должен вернуть null, если очередь пустая`() {
        val queue = Queue<Any>(1)

        val check = queue.poll()

        assertEquals(null, check)
    }

    @Test
    fun `poll должен вернуть элемент из головы очереди и удалить его, если очередь не пустая`() {
        val queue = Queue<Any>(1)
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
    fun `isEmpty должен возвращать true, если очередь пустая`() {
        val queue = Queue<Any>(1)

        val check = queue.isEmpty()

        assertEquals(true, check)
    }

    @Test
    fun `isEmpty должен возвращать false, если очередь не пустая`() {
        val queue = Queue<Any>(1)
        queue.offer(10)

        val check = queue.isEmpty()

        assertEquals(false, check)
    }

}