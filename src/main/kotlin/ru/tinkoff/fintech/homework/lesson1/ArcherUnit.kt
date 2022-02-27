package ru.tinkoff.fintech.homework.lesson1

class ArcherUnit(name : String) : Unit(name) {
    private var attackDamage : Double = 10.0 + agility
    private var attackType : String = "range"
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

    override fun getAttackType() : String{
        return attackType
    }
    override fun getAttackDamage(): Double {
        return attackDamage
    }
    override fun incAttackDamage(count : Int) {
        attackDamage += agility * count
    }
}