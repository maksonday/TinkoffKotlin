package ru.tinkoff.fintech.homework.lesson8

class GetSum(private val list: List<Int>, private val sum: Sum) : Runnable {
    override fun run() {
        val sliceSum = list.sum()
        synchronized(sum) {
            sum.value = sum.value + sliceSum
        }
    }
}