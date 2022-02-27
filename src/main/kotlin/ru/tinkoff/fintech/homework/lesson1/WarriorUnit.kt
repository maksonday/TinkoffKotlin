package ru.tinkoff.fintech.homework.lesson1

class WarriorUnit(name : String) : Unit(name) {
    private var attackDamage : Double = 10.0 + strength
    private var attackType : String = "melee"
    private var armor : Int = 3
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
    override fun getAttackType() : String{
        return attackType
    }
    override fun getAttackDamage(): Double {
        return attackDamage
    }

    override fun incAttackDamage(count : Int) {
        attackDamage += strength * count
    }
    override fun showCharacteristics(){
        println("Characteristics for %s:".format(getName()))
        println("-AttackDamage: %.2f\n-Armor: %d\n-Intelligence: %.2f\n-Strength: %.2f\n-Agility: %.2f\n-Level: %d\n".format(getAttackDamage(), armor, intelligence, strength, agility, getLvl()))
    }
    fun addShield(){
        armor += 10
    }
    fun getArmor() : Int{
        return armor
    }
}