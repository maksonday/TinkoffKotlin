package ru.tinkoff.fintech.homework.lesson1

class Army(val name: String, private val units: List<Unit>){
    private var buffValue: Double = 2.0
    fun getBuff(coefficient: Int){
        incMainCharacteristic(coefficient)
        units.forEach { unit ->
            unit.incAttackDamage(coefficient)
            unit.buffMessage()
            unit.showCharacteristics()
        }
    }

    fun incAttackDamage(count: Int) {
        units.forEach { unit -> unit.incAttackDamage(count) }
    }

    private fun incMainCharacteristic(count: Int) {
        units.forEach { unit ->
            if (unit is WarriorUnit) unit.strength += count * buffValue
            if (unit is ArcherUnit) unit.agility += count * buffValue
            if (unit is MageUnit) unit.intelligence += count * buffValue
        }
    }

    fun getBuff() {
        incMainCharacteristic(1)
        units.forEach { unit ->
            unit.incAttackDamage(1)
            unit.buffMessage()
            unit.showCharacteristics()
        }
    }

    fun info() {
        units.forEach { unit ->
            println("Information block of ${unit.name}\n--")
            unit.showCharacteristics()
            println("AttackType is: ${unit.attackType}")
            unit.showPosition()
            println("--")
        }
    }

}