package ru.tinkoff.fintech.homework.lesson1

class HeroUnit(name : String, base : Unit) : Unit(name) {
    private var attackDamage : Double
    private lateinit var type : String
    private var armor : Int = 0
    private var baseLink : Unit = base

    override fun getAttackDamage(): Double {
        return attackDamage
    }

    private var attackType : String
    override fun getAttackType(): String {
        return attackType
    }

    init{
        this.attackType = base.getAttackType()
        this.attackDamage = 50.0
        this.intelligence = baseLink.intelligence
        this.strength = baseLink.strength
        this.agility = baseLink.agility
        setLvl(baseLink.getLvl())
        if (baseLink is WarriorUnit) {
            attackDamage += strength
            this.type = "Warrior"
            this.armor = (baseLink as WarriorUnit).getArmor()
        }
        if (baseLink is ArcherUnit) {
            attackDamage += agility
            this.type = "Archer"
        }
        if (baseLink is MageUnit) {
            attackDamage += intelligence
            this.type = "Mage"
        }
    }

    override fun getBuff(coefficient: Int) {
        incMainCharacteristic(coefficient)
        incAttackDamage(coefficient)
        buffMessage()
        showCharacteristics()
    }

    override fun incAttackDamage(count : Int) {
        if (type == "Warrior") attackDamage += count*strength
        if (type ==  "Archer") attackDamage += count*agility
        if (type == "Mage") attackDamage += count*intelligence
    }

    private fun incMainCharacteristic(count : Int){
        if (type == "Warrior") strength += count*buffValue
        if (type ==  "Archer") agility += count*buffValue
        if (type == "Mage") intelligence += count*buffValue
    }

    override fun getBuff() {
        incMainCharacteristic(1)
        incAttackDamage(1)
        buffMessage()
        showCharacteristics()
    }

    fun info(){
        println("Information block of %s\n--".format(getName()))
        showCharacteristics()
        println("-Armor: %d".format(armor))
        println("AttackType is: %s".format(attackType))
        showPosition()
        println("--")
    }

    fun addShield(){
        if (type == "Warrior") armor += 10
        else println("Only warriors can have shield!")
    }

}