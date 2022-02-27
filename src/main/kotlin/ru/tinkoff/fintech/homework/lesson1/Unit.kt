package ru.tinkoff.fintech.homework.lesson1

abstract class Unit(name : String) {
    var intelligence : Double = 1.0
    var strength : Double = 1.0
    var agility : Double = 1.0
    var buffValue : Double = 2.0
    private var pos : Position = Position(0, 0)
    private var lvl : Int = 1
    private var lvlUpBuff : Double = 1.5
    private var name : String

    init{
        this.name = name
    }
    fun getLvl() : Int{
        return lvl
    }
    fun setLvl(lvl : Int){
        this.lvl = lvl
    }
    open fun showCharacteristics(){
        println("Characteristics for %s:".format(name))
        println("-AttackDamage: %.2f\n-Intelligence: %.2f\n-Strength: %.2f\n-Agility: %.2f\n-Level: %d\n".format(getAttackDamage(), intelligence, strength, agility, lvl))
    }

    fun buffMessage(){
        println("Wow! Your unit has been buffed")
    }

    fun showPosition(){
        println("Position of unit %s is: [%d, %d]\n".format(name, pos.x, pos.y))
    }

    fun move(x : Int, y : Int){
        pos.x += x
        pos.y += y
    }
    fun getName() : String{
        return name
    }
    fun levelUp(count: Int){
        intelligence += count * lvlUpBuff
        strength += count * lvlUpBuff
        agility += count * lvlUpBuff
        incAttackDamage(count)
        lvl += count
        println("Lvl up! Current lvl of %s is: %d".format(name, lvl))
    }

    fun levelUp(){
        intelligence += lvlUpBuff
        strength += lvlUpBuff
        agility += lvlUpBuff
        incAttackDamage(1)
        lvl += 1
        println("Lvl up! Current lvl of %s is: %d".format(name, lvl))
    }


    abstract fun getBuff(coefficient : Int)
    abstract fun getBuff()
    abstract fun getAttackType() : String
    abstract fun getAttackDamage() : Double
    abstract fun incAttackDamage(count : Int)
}