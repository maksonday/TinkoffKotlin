package ru.tinkoff.fintech.homework.lesson1

abstract class Unit(val name: String) {
    internal var intelligence: Double = 1.0
    internal var strength: Double = 1.0
    internal var agility: Double = 1.0
    internal var buffValue: Double = 2.0
    private var pos: Position = Position(0, 0)
    open var attackDamage: Double = 0.0
    open lateinit var attackType: String
    internal var lvl: Int = 1
    private var lvlUpBuff: Double = 1.5

    open fun showCharacteristics() {
        println("Characteristics for $name:")
        println("-AttackDamage: $attackDamage\n-Intelligence: $intelligence\n-Strength: $strength\n-Agility: $agility\n-Level: $lvl\n")
    }

    fun buffMessage() {
        println("Wow! Your unit has been buffed")
    }

    fun showPosition() {
        println("Position of unit $name is: [${pos.x}, ${pos.y}]")
    }

    fun move(x: Int, y: Int) {
        pos.x += x
        pos.y += y
    }

    fun levelUp(count: Int) {
        intelligence += count * lvlUpBuff
        strength += count * lvlUpBuff
        agility += count * lvlUpBuff
        incAttackDamage(count)
        lvl += count
        println("Lvl up! Current lvl of $name is: $lvl")
    }

    fun levelUp() {
        intelligence += lvlUpBuff
        strength += lvlUpBuff
        agility += lvlUpBuff
        incAttackDamage(1)
        lvl += 1
        println("Lvl up! Current lvl of $name is: $lvl")
    }


    abstract fun getBuff(coefficient: Int)
    abstract fun getBuff()
    abstract fun incAttackDamage(count: Int)
}