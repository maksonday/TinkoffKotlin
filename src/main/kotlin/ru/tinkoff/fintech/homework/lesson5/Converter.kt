package ru.tinkoff.fintech.homework.lesson5

import java.util.*

object Converter {
    private const val CURRENCY_RATE = 33

    private val DICT = mapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "yo",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "y",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sch",
        'ъ' to "",
        'ы' to "y",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )

    private fun String.transliterate(): String {
        val flagFirstUpper = this[0].isUpperCase()
        fun flagAll(): Boolean {
            var ans = true
            for (i in 0 until this.length) {
                if (Character.isLowerCase(this[i])) {
                    ans = false
                    break
                }
            }
            return ans
        }

        val flagAllUpper = flagAll()
        val temp =
            this.map { if (DICT.containsKey(it.lowercaseChar())) DICT[it.lowercaseChar()] else it.lowercaseChar() }
                .joinToString("")
        return if (flagFirstUpper && !flagAllUpper) temp.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        else if (flagAllUpper) temp.uppercase()
        else temp
    }

    fun transform(list: Collection<Car>): Collection<Car> {
        return list.asSequence().map {
            Car(
                it.name.transliterate(),
                it.brand.transliterate(),
                it.typeOfBack.transliterate(),
                it.price * CURRENCY_RATE,
                it.consumption
            )
        }.sortedBy {
            it.price
        }.toList()
    }

    fun groupByTypeOfBack(list: Collection<Car>): Collection<Car> {
        return list.groupBy { it.typeOfBack }.values.flatten()
    }

    fun filterFirstThree(list: Collection<Car>, predicate: (Car) -> (Boolean)): Collection<Car> {
        return list.asSequence().filter { predicate(it) }.take(3).map {
            Car(
                it.name.transliterate(),
                it.brand.transliterate(),
                it.typeOfBack.transliterate(),
                it.price,
                it.consumption
            )
        }.toList()
    }
}