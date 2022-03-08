package ru.tinkoff.fintech.homework.lesson1

class ArcherUnit(name: String) : Unit(name) {
    override var attackDamage = 10.0 + agility
    override var attackType = "range"

    override fun getBuff(coefficient: Int) {
        agility += coefficient * buffValue
        incAttackDamage(coefficient)
        buffMessage()
        showCharacteristics()
    }

    override fun getBuff() {
        agility += buffValue
        incAttackDamage(1)
        buffMessage()
        showCharacteristics()
    }

    override fun incAttackDamage(count: Int) {
        attackDamage += agility * count
    }
}