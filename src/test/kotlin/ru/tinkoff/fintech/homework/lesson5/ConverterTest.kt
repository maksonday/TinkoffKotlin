package ru.tinkoff.fintech.homework.lesson5

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ConverterTest {
    @ParameterizedTest
    @CsvSource(
        "Тойота, Toyota",
        "345435@#!@#12, 345435@#!@#12",
        "минивен, miniven",
        "самая крутая машина, the coolest car"
    )
    fun `translate должен преобразовать строку на русском языке в строку на английском, если исходное слово есть в словаре, иначе оставить строку как есть`(
        initialString: String,
        convertedString: String
    ) {
        val method = Converter.javaClass.getDeclaredMethod("translate", String::class.java)
        method.isAccessible = true

        val converted = method.invoke(Converter, initialString)

        assertEquals(convertedString, converted)
    }

    private val carList = listOf(
        Car("обычная машина", "Тойота", "седан", 20000.0, 10.0),
        Car("самая крутая машина", "БМВ", "минивен", 100000.0, 50.0),
        Car("самая быстрая машина", "Феррари", "седан", 120000.0, 40.0),
        Car("быстрая машина", "Киа", "седан", 30000.0, 20.0),
        Car("крутая машина", "Ауди", "минивен", 40000.0, 50.0)
    )

    @Test
    fun `transform должен преобразовать поля name, brand и typeOfBack с русского на английский, а поле price должно измениться в соответствии с курсом валюты CURRENCY_RATE, полученная коллекция должна быть отсортирована по возрастанию price`() {
        val converted = Converter.transform(carList)

        assertAll(
            {
                assertEquals(Car("casual car", "Toyota", "sedan", 660000.0, 10.0), converted.toList()[0])
                assertEquals(Car("fast car", "Kia", "sedan", 990000.0, 20.0), converted.toList()[1])
                assertEquals(Car("cool car", "Audi", "miniven", 1320000.0, 50.0), converted.toList()[2])
                assertEquals(Car("the coolest car", "BMW", "miniven", 3300000.0, 50.0), converted.toList()[3])
                assertEquals(Car("the fastest car", "Ferrari", "sedan", 3960000.0, 40.0), converted.toList()[4])
            }
        )
    }

    @Test
    fun `groupByTypeOfBack должен группировать коллекцию из Car по полю typeOfBack`() {
        val converted = Converter.groupByTypeOfBack(carList)

        assertAll(
            {
                assertEquals(Car("обычная машина", "Тойота", "седан", 20000.0, 10.0), converted.toList()[0])
                assertEquals(Car("самая быстрая машина", "Феррари", "седан", 120000.0, 40.0), converted.toList()[1])
                assertEquals(Car("быстрая машина", "Киа", "седан", 30000.0, 20.0), converted.toList()[2])
                assertEquals(Car("самая крутая машина", "БМВ", "минивен", 100000.0, 50.0), converted.toList()[3])
                assertEquals(Car("крутая машина", "Ауди", "минивен", 40000.0, 50.0), converted.toList()[4])
            }
        )
    }

    @Test
    fun `filter должен выбрать элементы, удовлетворяющие предикату, например, Car с price меньше 100000, затем перевести поля name, brand, typeOfBack с русского на английский и взять первые 3 элемента`() {
        val converted = Converter.filterFirstThree(carList) { it.price < 100000.0 }

        assertAll(
            {
                assertEquals(3, converted.size)
                assertEquals(Car("casual car", "Toyota", "sedan", 20000.0, 10.0), converted.toList()[0])
                assertEquals(Car("fast car", "Kia", "sedan", 30000.0, 20.0), converted.toList()[1])
                assertEquals(Car("cool car", "Audi", "miniven", 40000.0, 50.0), converted.toList()[2])
            }
        )
    }
}