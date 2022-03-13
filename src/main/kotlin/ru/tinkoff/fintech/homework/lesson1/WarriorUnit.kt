package ru.tinkoff.fintech.homework.lesson1

class WarriorUnit(name: String) : Unit(name) {
    private var armor: Int = 3
    override var attackDamage = 10.0 + strength
    override var attackType = "melee"

    override fun getBuff(coefficient: Int) {
        strength += coefficient * buffValue
        incAttackDamage(coefficient)
        buffMessage()
        showCharacteristics()
    }

    override fun getBuff() {
        strength += buffValue
        incAttackDamage(1)
        buffMessage()
        showCharacteristics()
    }

    override fun incAttackDamage(count: Int) {
        attackDamage += strength * count
    }

    override fun showCharacteristics() {
        println("Characteristics for $name:")
        println("-AttackDamage: $attackDamage\n-Armor: $armor\n-Intelligence: $intelligence\n-Strength: $strength\n-Agility: $agility\n-Level: $lvl\n")
    }

    fun addShield() {
        armor += 10
    }
}