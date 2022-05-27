package ru.tinkoff.fintech.homework.lesson10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.tinkoff.fintech.homework.lesson10.db.EventRepository
import ru.tinkoff.fintech.homework.lesson10.model.EventStatus
import java.lang.Thread.sleep
import org.awaitility.Awaitility.await
import org.awaitility.kotlin.matches
import org.awaitility.kotlin.untilCallTo

@SpringBootTest
class BrokerTest {
    @Autowired
    private lateinit var eventRepo: EventRepository

    @Test
    fun `test update status to DONE`() {
        await() untilCallTo {
            eventRepo.findAll()
        } matches {
            it!!.all { event -> event.status == EventStatus.DONE }
        }

        sleep(2000)
        val result = eventRepo.findAll()

        result.forEach{
            assertEquals(EventStatus.DONE, it.status)
        }
    }
}