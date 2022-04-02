package ru.tinkoff.fintech.homework.lesson1

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(MockKExtension::class)
class ArmyTest {
    @ParameterizedTest
    @CsvSource("1, 1.0, 3.0, 14.0", "2, 2.0, 6.0, 23.0")
    fun `тестируем army getBuff с count с помощью mockk`(
        count: Int,
        initCharacteristic: Double,
        mainCharacteristic: Double,
        attackDmg: Double
    ) {
        val warrior = mockk<WarriorUnit>(relaxed = true)
        every {
            warrior.strength
        } answers { fieldValue }
        every {
            warrior.strength = any()
        } propertyType Double::class answers { fieldValue += value + initCharacteristic }
        every {
            warrior.agility
        } answers { fieldValue + 1.0 }
        every {
            warrior.intelligence
        } answers { fieldValue + 1.0 }
        every {
            warrior.attackDamage
        } answers { attackDmg }
        val army = Army("myArmy", listOf(warrior))

        army.getBuff(count)

        assertAll(
            { verify(exactly = 1) { army.getBuff(count) } },
            {
                assertEquals(mainCharacteristic, warrior.strength)
                assertEquals(1.0, warrior.agility)
                assertEquals(1.0, warrior.intelligence)
                assertEquals(attackDmg, warrior.attackDamage)
            }
        )
    }

    @ParameterizedTest
    @CsvSource("1.0, 3.0, 14.0", "2.0, 4.0, 15.0")
    fun `тестируем army getBuff с помощью mockk`(
        initCharacteristic: Double,
        mainCharacteristic: Double,
        attackDmg: Double
    ) {
        val warrior = mockk<WarriorUnit>(relaxed = true)
        every {
            warrior.strength
        } answers { fieldValue }
        every {
            warrior.strength = any()
        } propertyType Double::class answers { fieldValue += value + initCharacteristic }
        every {
            warrior.agility
        } answers { fieldValue + 1.0 }
        every {
            warrior.intelligence
        } answers { fieldValue + 1.0 }
        every {
            warrior.attackDamage
        } answers { attackDmg }
        val army = Army("myArmy", listOf(warrior))

        army.getBuff()

        assertAll(
            { verify(exactly = 1) { army.getBuff() } },
            {
                assertEquals(mainCharacteristic, warrior.strength)
                assertEquals(1.0, warrior.agility)
                assertEquals(1.0, warrior.intelligence)
                assertEquals(attackDmg, warrior.attackDamage)
            }
        )
    }


    @ParameterizedTest
    @CsvSource("1, 12.0", "2, 13.0")
    fun `тестируем army incAttackDamage с помощью mockk`(count: Int, attackDmg: Double) {
        val warrior = mockk<WarriorUnit>(relaxed = true)
        every {
            warrior.attackDamage
        } answers { attackDmg }
        val army = Army("myArmy", listOf(warrior))

        army.incAttackDamage(count)

        verify(exactly = 1) { army.incAttackDamage(count) }
        assertEquals(attackDmg, warrior.attackDamage)
    }

}

