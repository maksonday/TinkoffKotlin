package ru.tinkoff.fintech.homework.lesson5

class CarService {
    companion object {
        private const val CURRENCY_RATE = 33
        private val DICT = mapOf(
            "Тойота" to "Toyota",
            "Хонда" to "Honda",
            "Лада" to "Lada",
            "Феррари" to "Ferrari",
            "Киа" to "Kia",
            "БМВ" to "BMW",
            "Ауди" to "Audi",
            "минивен" to "miniven",
            "седан" to "sedan",
            "самая крутая машина" to "the coolest car",
            "самая быстрая машина" to "the fastest car",
            "обычная машина" to "casual car",
            "крутая машина" to "cool car",
            "быстрая машина" to "fast car"
        )
    }

    private fun String.translate(): String = DICT[this] ?: throw TranslateException("Unknown word")

    fun transform(list: Collection<Car>): Collection<Car> {
        return list.asSequence().map {
            Car(
                it.name.translate(),
                it.brand.translate(),
                it.typeOfBack.translate(),
                it.price * CURRENCY_RATE,
                it.consumption
            )
        }.sortedBy {
            it.price
        }.toList()
    }

    fun groupByTypeOfBack(list: Collection<Car>): Map<String, List<Car>> {
        return list.groupBy { it.typeOfBack }
    }

    fun filterFirstThree(list: Collection<Car>, predicate: (Car) -> (Boolean)): Collection<Car> {
        return list.asSequence().filter { predicate(it) }.take(3).map {
            Car(
                it.name.translate(),
                it.brand.translate(),
                it.typeOfBack.translate(),
                it.price,
                it.consumption
            )
        }.toList()
    }
}