package ru.tinkoff.fintech.homework.lesson1

fun main() {
    val warrior = WarriorUnit("Steve")
    warrior.levelUp()
    warrior.levelUp(25)
    warrior.getBuff()
    warrior.getBuff(5)
    warrior.move(1, 2)
    warrior.showPosition()
    warrior.addShield()
    warrior.showCharacteristics()

    val archer = ArcherUnit("Tommy")
    archer.levelUp()
    archer.levelUp(25)
    archer.getBuff()
    archer.getBuff(5)
    archer.move(2, 2)
    archer.showPosition()
    archer.showCharacteristics()

    val mage = MageUnit("Gandalf")
    mage.levelUp()
    mage.levelUp(25)
    mage.getBuff()
    mage.getBuff(5)
    mage.move(2, 1)
    mage.showPosition()
    mage.showCharacteristics()

    val army = Army("My army", listOf(warrior, mage, archer))
    army.getBuff(5)
    army.getBuff()
    army.incAttackDamage(10)
    army.info()
}