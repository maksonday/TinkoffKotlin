package ru.tinkoff.fintech.homework.lesson5

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ConverterTest {
    @ParameterizedTest
    @CsvSource(
        "Тойота, Toyota",
        "тойота, toyota",
        "тОйоТа, toyota",
        "ТОйОтА, Toyota",
        "ТОЙОТА, TOYOTA",
        "345435@#!@#12, 345435@#!@#12",
        "ТойОта123, Toyota123"
    )
    fun `transliterate должен преобразовывать строки на русском языке в строки на английском, приводя строку к нижнему регистру, но при этом сохраняя первую заглавную букву`(
        initialString: String,
        convertedString: String
    ) {
        val method = Converter.javaClass.getDeclaredMethod("transliterate", String::class.java)
        method.isAccessible = true

        val converted = method.invoke(Converter, initialString)

        assertEquals(convertedString, converted)
    }

    private val carList = listOf(
        Car("у6", "Тойота", "седан", 60000.0, 10.0),
        Car("у8", "БМВ", "минивен", 30000.0, 50.0),
        Car("у7", "Лада", "седан", 40000.0, 40.0),
        Car("икс10", "Тойота", "седан", 60000.0, 20.0),
        Car("икс11", "Тойота", "минивен", 120000.0, 30.0)
    )

    @Test
    fun `transform должен преобразовать поля name, brand и typeOfBack с русского на английский, а поле price должно измениться в соответствии с курсом валюты CURRENCY_RATE, полученная коллекция должна быть отсортирована по возрастанию price`() {
        val converted = Converter.transform(carList)

        assertAll(
            {
                assertEquals(Car("u8", "BMV", "miniven", 990000.0, 50.0), converted.toList()[0])
                assertEquals(Car("u7", "Lada", "sedan", 1320000.0, 40.0), converted.toList()[1])
                assertEquals(Car("u6", "Toyota", "sedan", 1980000.0, 10.0), converted.toList()[2])
                assertEquals(Car("iks10", "Toyota", "sedan", 1980000.0, 20.0), converted.toList()[3])
                assertEquals(Car("iks11", "Toyota", "miniven", 3960000.0, 30.0), converted.toList()[4])
            }
        )
    }

    @Test
    fun `groupByTypeOfBack должен группировать коллекцию из Car по полю typeOfBack`() {
        val converted = Converter.groupByTypeOfBack(carList)

        assertAll(
            {
                assertEquals(Car("у6", "Тойота", "седан", 60000.0, 10.0), converted.toList()[0])
                assertEquals(Car("у7", "Лада", "седан", 40000.0, 40.0), converted.toList()[1])
                assertEquals(Car("икс10", "Тойота", "седан", 60000.0, 20.0), converted.toList()[2])
                assertEquals(Car("у8", "БМВ", "минивен", 30000.0, 50.0), converted.toList()[3])
                assertEquals(Car("икс11", "Тойота", "минивен", 120000.0, 30.0), converted.toList()[4])
            }
        )
    }

    @Test
    fun `filter должен выбрать элементы, удовлетворяющие предикату, например, Car с price меньше 100000, затем перевести поля name, brand, typeOfBack с русского на английский и взять первые 3 элемента`() {
        val converted = Converter.filterFirstThree(carList) { it.price < 100000.0 }

        assertAll(
            {
                assertEquals(3, converted.size)
                assertEquals(Car("u8", "BMV", "miniven", 30000.0, 50.0), converted.toList()[1])
                assertEquals(Car("u7", "Lada", "sedan", 40000.0, 40.0), converted.toList()[2])
                assertEquals(Car("u6", "Toyota", "sedan", 60000.0, 10.0), converted.toList()[0])
            }
        )
    }
}