package ru.tinkoff.fintech.homework.lesson1

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(MockKExtension::class)
internal class ArmyMockTest{
    @ParameterizedTest
    @CsvSource("1, 1.0, 3.0, 14.0", "2, 2.0, 6.0, 23.0")
    fun `тестируем army getBuff с count с помощью spy`(count : Int, initCharacteristic : Double, mainCharacteristic : Double, attackDmg : Double){
        val warrior = WarriorUnit("human")
        warrior.strength = initCharacteristic
        val archer = ArcherUnit("elf")
        archer.agility = initCharacteristic
        val mage = MageUnit("gnome")
        mage.intelligence = initCharacteristic
        val army = Army("OurArmy", listOf(warrior, archer, mage))
        val spyArmy = spyk(army)

        spyArmy.getBuff(count)

        verify (exactly = 1){
            spyArmy.getBuff(count)
        }
        assertAll( "Checking equality of characteristics",
            {
                assertEquals(listOf(mainCharacteristic, 1.0, 1.0), listOf(warrior.strength, warrior.agility, warrior.intelligence))
                assertEquals(listOf(1.0, mainCharacteristic, 1.0), listOf(archer.strength, archer.agility, archer.intelligence))
                assertEquals(listOf(1.0, 1.0, mainCharacteristic), listOf(mage.strength, mage.agility, mage.intelligence))
                assertEquals(listOf(attackDmg, attackDmg, attackDmg), listOf(warrior.attackDamage, archer.attackDamage, mage.attackDamage))
            }
        )
    }

    @ParameterizedTest
    @CsvSource("1.0, 3.0, 14.0", "2.0, 4.0, 15.0")
    fun `тестируем army getBuff с помощью spy`(initCharacteristic : Double, mainCharacteristic : Double, attackDmg : Double){
        val warrior = WarriorUnit("human")
        warrior.strength = initCharacteristic
        val archer = ArcherUnit("elf")
        archer.agility = initCharacteristic
        val mage = MageUnit("gnome")
        mage.intelligence = initCharacteristic
        val army = Army("OurArmy", listOf(warrior, archer, mage))
        val spyArmy = spyk(army)

        spyArmy.getBuff()

        verify (exactly = 1){
            spyArmy.getBuff()
        }
        assertAll( "Checking equality of characteristics",
            {
                assertEquals(listOf(mainCharacteristic, 1.0, 1.0), listOf(warrior.strength, warrior.agility, warrior.intelligence))
                assertEquals(listOf(1.0, mainCharacteristic, 1.0), listOf(archer.strength, archer.agility, archer.intelligence))
                assertEquals(listOf(1.0, 1.0, mainCharacteristic), listOf(mage.strength, mage.agility, mage.intelligence))
                assertEquals(listOf(attackDmg, attackDmg, attackDmg), listOf(warrior.attackDamage, archer.attackDamage, mage.attackDamage))
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

        spyArmy.incAttackDamage(count)

        verify (exactly = 1){
            spyArmy.incAttackDamage(count)
        }
        assertEquals(listOf(attackDmg, attackDmg, attackDmg), listOf(warrior.attackDamage, archer.attackDamage, mage.attackDamage))
    }

}

