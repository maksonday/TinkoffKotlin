package ru.tinkoff.fintech.homework.lesson1

import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

@ExtendWith(MockKExtension::class)
internal class ArmyTest {
    @ParameterizedTest
    @CsvSource("1", "2")
    fun `тестируем army getBuff с count с помощью mockk`(count: Int) {
        val army = mockk<Army> {
            every { getBuff(count) } returns Unit
        }

        assertAll(
            { assertEquals(Unit, army.getBuff(count)) },
            { verify(exactly = 1) { army.getBuff(count) } }
        )
    }

    @Test
    fun `тестируем army getBuff с помощью mockk`() {
        val army = mockk<Army> {
            every { getBuff() } returns Unit
        }

        assertAll(
            { assertEquals(Unit, army.getBuff()) },
            { verify(exactly = 1) { army.getBuff() } }
        )
    }


    @ParameterizedTest
    @CsvSource("1", "5")
    fun `тестируем army incAttackDamage с помощью mockk`(count: Int) {
        val army = mockk<Army> {
            every { incAttackDamage(count) } returns Unit
        }

        assertAll(
            { assertEquals(Unit, army.incAttackDamage(count)) },
            { verify(exactly = 1) { army.incAttackDamage(count) } }
        )
    }

}

