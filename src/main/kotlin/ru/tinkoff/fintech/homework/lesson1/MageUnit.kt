package ru.tinkoff.fintech.homework.lesson1

class MageUnit(name : String) : Unit(name) {
    override var attackDamage = 10.0 + intelligence
    override var attackType = "range"
    override fun getBuff(coefficient: Int) {
        intelligence += coefficient * buffValue
        incAttackDamage(coefficient)
        buffMessage()
        showCharacteristics()
    }

    override fun getBuff() {
        intelligence += buffValue
        incAttackDamage(1)
        buffMessage()
        showCharacteristics()
    }

    override fun incAttackDamage(count : Int) {
        attackDamage += intelligence * count
    }
}