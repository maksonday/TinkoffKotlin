package ru.tinkoff.fintech.homework.lesson1

class MageUnit(name : String) : Unit(name) {
    private var attackDamage : Double = 10.0 + intelligence
    private var attackType : String = "range"
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

    override fun getAttackType() : String{
        return attackType
    }
    override fun getAttackDamage(): Double {
        return attackDamage
    }
    override fun incAttackDamage(count : Int) {
        attackDamage += intelligence * count
    }
}