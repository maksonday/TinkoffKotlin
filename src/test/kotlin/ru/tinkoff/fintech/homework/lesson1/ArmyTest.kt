package ru.tinkoff.fintech.homework.lesson1

import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(MockKExtension::class)
internal class ArmyTest {
    @ParameterizedTest
    @CsvSource("1, 1.0, 3.0, 14.0", "2, 2.0, 6.0, 23.0")
    fun `тестируем army getBuff с count с помощью spy`(count : Int, initCharacteristic : Double, mainCharacteristic : Double, attackDmg : Double) {
        val warrior = WarriorUnit("human")
        warrior.strength = initCharacteristic
        val archer = ArcherUnit("elf")
        archer.agility = initCharacteristic
        val mage = MageUnit("gnome")
        mage.intelligence = initCharacteristic
        val army = Army("OurArmy", listOf(warrior, archer, mage))
        val spyArmy = spyk(army)

        assertAll(
            { assertEquals(Unit, spyArmy.getBuff(count)) },
            { verify(exactly = 1) { spyArmy.getBuff(count) } },
            {
                assertEquals(mainCharacteristic, warrior.strength)
                assertEquals(1.0,  warrior.agility)
                assertEquals(1.0, warrior.intelligence)
            },
            {
                assertEquals(1.0, archer.strength)
                assertEquals(mainCharacteristic, archer.agility)
                assertEquals(1.0, archer.intelligence)
            },
            {
                assertEquals(1.0, mage.strength)
                assertEquals(1.0, mage.agility)
                assertEquals(mainCharacteristic, mage.intelligence)
            },
            {
                assertEquals(attackDmg, warrior.attackDamage)
                assertEquals(attackDmg, archer.attackDamage)
                assertEquals(attackDmg, mage.attackDamage)
            }
        )
    }

    @ParameterizedTest
    @CsvSource("1.0, 3.0, 14.0", "2.0, 4.0, 15.0")
    fun `тестируем army getBuff с помощью spy`(initCharacteristic : Double, mainCharacteristic : Double, attackDmg : Double) {
        val warrior = WarriorUnit("human")
        warrior.strength = initCharacteristic
        val archer = ArcherUnit("elf")
        archer.agility = initCharacteristic
        val mage = MageUnit("gnome")
        mage.intelligence = initCharacteristic
        val army = Army("OurArmy", listOf(warrior, archer, mage))
        val spyArmy = spyk(army)

        assertAll(
            { assertEquals(Unit, spyArmy.getBuff()) },
            { verify(exactly = 1) { spyArmy.getBuff() } },
            {
                assertEquals(mainCharacteristic, warrior.strength)
                assertEquals(1.0,  warrior.agility)
                assertEquals(1.0, warrior.intelligence)
            },
            {
                assertEquals(1.0, archer.strength)
                assertEquals(mainCharacteristic, archer.agility)
                assertEquals(1.0, archer.intelligence)
            },
            {
                assertEquals(1.0, mage.strength)
                assertEquals(1.0, mage.agility)
                assertEquals(mainCharacteristic, mage.intelligence)
            },
            {
                assertEquals(attackDmg, warrior.attackDamage)
                assertEquals(attackDmg, archer.attackDamage)
                assertEquals(attackDmg, mage.attackDamage)
            }
        )
    }


    @ParameterizedTest
    @CsvSource("1, 12.0", "2, 13.0")
    fun `тестируем army incAttackDamage с помощью spy`(count : Int, attackDmg : Double){
        val warrior = WarriorUnit("human")
        val archer = ArcherUnit("elf")
        val mage = MageUnit("gnome")
        val army = Army("OurArmy", listOf(warrior, archer, mage))
        val spyArmy = spyk(army)

        assertAll(
            { assertEquals(Unit, spyArmy.incAttackDamage(count)) },
            { verify(exactly = 1) { spyArmy.incAttackDamage(count) } },
            {
                assertEquals(attackDmg, warrior.attackDamage)
                assertEquals(attackDmg, archer.attackDamage)
                assertEquals(attackDmg, mage.attackDamage)
            }
        )
    }

}

