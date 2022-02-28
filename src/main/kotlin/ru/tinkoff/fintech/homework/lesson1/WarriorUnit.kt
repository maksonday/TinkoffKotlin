package ru.tinkoff.fintech.homework.lesson1

class WarriorUnit(name : String) : Unit(name) {
    var armor : Int = 3
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

    override fun incAttackDamage(count : Int) {
        attackDamage += strength * count
    }
    override fun showCharacteristics(){
        println("Characteristics for $name:")
        println("-AttackDamage: %.2f\n-Armor: %d\n-Intelligence: %.2f\n-Strength: %.2f\n-Agility: %.2f\n-Level: $lvl\n".format(attackDamage, armor, intelligence, strength, agility))
    }
    fun addShield(){
        armor += 10
    }
}