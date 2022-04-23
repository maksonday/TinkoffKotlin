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
        for (i in 0 until 10000) {
            val myRandomValues = List(2000) { Random.nextInt(2000, 10000) }
            val sum = Sum()

            threadPool.execute(GetSum(myRandomValues.slice(0 until 1000), sum))
            threadPool.execute(GetSum(myRandomValues.slice(1000 until 2000), sum))
            val checkShutdown = threadPool.shutdown()

            while (!checkShutdown) {
                assertEquals(sum.value, myRandomValues.sum())
            }
        }
    }

    @Test
    fun `пытаемся выполнить задачи, которые требуют значительного времени или просто произошла какая-то ошибка в ходе их выполнения, сразу после добавления нового задания пытаемся закрыть пул потоков, но получим исключение по одной из перечисленных причин из-за таймаута`() {
        assertFailsWith<RuntimeException>(
            message = "An error occurred: can't stop thread %s",
            block = {
                val threadPool = ThreadPool(1)
                threadPool.execute(DoNothing())
                threadPool.shutdown()
            }
        )
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

class GetSum(private val list: List<Int>, private val sum: Sum) : Runnable {
    override fun run() {
        val sliceSum = list.sum()
        sleep(60000)
        synchronized(sum) {
            sum.value = sum.value + sliceSum
        }
    }
}

data class Sum(
    var value: Int = 0
) : Object()

class DoNothing : Runnable {
    override fun run() {
        sleep(60000)
    }
}