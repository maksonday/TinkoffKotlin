package ru.tinkoff.fintech.homework.lesson1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class WarriorTest{
    @ParameterizedTest
    @CsvSource("5, 11.0, 66.0", "10, 21.0, 221.0")
    fun `когда воин получает бафф, должны увеличиться его сила и урон от атаки`(count : Int, strength : Double, attackDmg : Double){
        val warrior = WarriorUnit("human")

        warrior.getBuff(count)

        assertAll("Warrior's strength and attack damage should increase",
            { assertEquals(strength, warrior.strength) },
            { assertEquals(attackDmg, warrior.attackDamage) }
        )
    }
}