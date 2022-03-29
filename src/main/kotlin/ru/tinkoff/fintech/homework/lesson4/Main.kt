package ru.tinkoff.fintech.homework.lesson4

fun main() {
    val myQueue = Queue<Int>(10)

    myQueue.offer(10)
    myQueue.offer(20)
    myQueue.offer(30)
    myQueue.offer(50)

    val elem = myQueue.element()
    val rm = myQueue.remove()
    val pk = myQueue.peek()
    val pl = myQueue.poll()
    println("Results:\n-element: $elem\n-remove: $rm\n-peek: $pk\n-poll: $pl")

    while (!myQueue.isEmpty()) {
        println(myQueue.poll())
    }

    val myStack = Stack<Int>(10)
    myStack.push(10)
    myStack.push(20)
    myStack.push(30)
    myStack.push(40)
    myStack.push(50)

    val last = myStack.pop()
    val showLast = myStack.peek()
    println("Results:\n-pop: $last\n-peek: $showLast")

    while (!myStack.isEmpty()) {
        println(myStack.pop())
    }
}