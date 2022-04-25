package ru.tinkoff.fintech.homework.lesson8


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep
import kotlin.random.Random
import kotlin.test.assertFailsWith

class ThreadPoolTest {
    @Test
    fun `сгенерируем список случайных целых положительных чисел и посчитаем сумму его элементов обычным способом и через разбиение на списки + ThreadPool`() {
        val threadPool = ThreadPool(4)
        val myRandomValues = List(2000) { Random.nextInt(2000, 10000) }
        val sum = Sum()

        threadPool.execute(GetSum(myRandomValues.slice(0 until 1000), sum))
        threadPool.execute(GetSum(myRandomValues.slice(1000 until 2000), sum))
        val checkFinished = threadPool.shutdown()

        assertEquals(myRandomValues.sum(), sum.value)
        assertEquals(true, checkFinished)
    }

    @Test
    fun `пытаемся выполнить задачи, которые требуют значительного времени или просто произошла какая-то ошибка в ходе их выполнения, сразу после добавления нового задания пытаемся закрыть пул потоков, но получим исключение по одной из перечисленных причин из-за таймаута`() {
        //таймаут - опциональный параметр конструктора ThreadPool, если не инициализирован, используется значение таймаута по умолчанию(TIMEOUT)
        //в данном тесте возьмем таймаут 1 мс
        val threadPool = ThreadPool(1, 1)

        threadPool.execute(DoNothing())
        val checkFinished = threadPool.shutdown()

        assertEquals(false, checkFinished)
    }

    @Test
    fun `при попытке создать ThreadPool с неположительным аргументом должно выкидываться исключение`() {
        assertFailsWith<IllegalArgumentException>(
            message = "Amount of threads must be a positive number",
            block = {
                val threadPool = ThreadPool(0)
            }
        )
    }

    @Test
    fun `при попытке создать ThreadPool с кол-вом потоком, большим чем число ядер, должно выкидываться исключение`() {
        assertFailsWith<IllegalArgumentException>(
            message = "Too much threads. Amount of threads in pool must be equal or less than ${
                Runtime.getRuntime().availableProcessors()
            }.",
            block = {
                val threadPool = ThreadPool(10)
            }
        )
    }
}