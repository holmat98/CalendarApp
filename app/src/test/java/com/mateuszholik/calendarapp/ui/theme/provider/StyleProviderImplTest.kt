package com.mateuszholik.calendarapp.ui.theme.provider

import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import org.assertj.core.api.Assertions.assertThat
import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.LocalDate

internal class StyleProviderImplTest {

    private val currentDateProvider = mockk<CurrentDateProvider>()

    private val styleProvider = StyleProviderImpl(currentDateProvider)

    @TestFactory
    fun checkStyleProvider() =
        listOf(
            TestObject(
                date = LocalDate.of(2023, 1, 1),
                expectedResult = StyleType.WINTER
            ),
            TestObject(
                date = LocalDate.of(2023, 1, 20),
                expectedResult = StyleType.WINTER
            ),
            TestObject(
                date = LocalDate.of(2023, 2, 5),
                expectedResult = StyleType.WINTER
            ),
            TestObject(
                date = LocalDate.of(2023, 2, 28),
                expectedResult = StyleType.WINTER
            ),
            TestObject(
                date = LocalDate.of(2023, 3, 7),
                expectedResult = StyleType.WINTER
            ),
            TestObject(
                date = LocalDate.of(2023, 3, 22),
                expectedResult = StyleType.SPRING
            ),
            TestObject(
                date = LocalDate.of(2023, 4, 2),
                expectedResult = StyleType.SPRING
            ),
            TestObject(
                date = LocalDate.of(2023, 4, 26),
                expectedResult = StyleType.SPRING
            ),
            TestObject(
                date = LocalDate.of(2023, 5, 1),
                expectedResult = StyleType.SPRING
            ),
            TestObject(
                date = LocalDate.of(2023, 5, 31),
                expectedResult = StyleType.SPRING
            ),
            TestObject(
                date = LocalDate.of(2023, 6, 11),
                expectedResult = StyleType.SPRING
            ),
            TestObject(
                date = LocalDate.of(2023, 6, 23),
                expectedResult = StyleType.SUMMER
            ),
            TestObject(
                date = LocalDate.of(2023, 7, 7),
                expectedResult = StyleType.SUMMER
            ),
            TestObject(
                date = LocalDate.of(2023, 7, 28),
                expectedResult = StyleType.SUMMER
            ),
            TestObject(
                date = LocalDate.of(2023, 8, 2),
                expectedResult = StyleType.SUMMER
            ),
            TestObject(
                date = LocalDate.of(2023, 8, 29),
                expectedResult = StyleType.SUMMER
            ),
            TestObject(
                date = LocalDate.of(2023, 9, 3),
                expectedResult = StyleType.SUMMER
            ),
            TestObject(
                date = LocalDate.of(2023, 9, 24),
                expectedResult = StyleType.AUTUMN
            ),
            TestObject(
                date = LocalDate.of(2023, 10, 6),
                expectedResult = StyleType.AUTUMN
            ),
            TestObject(
                date = LocalDate.of(2023, 10, 14),
                expectedResult = StyleType.AUTUMN
            ),
            TestObject(
                date = LocalDate.of(2023, 11, 2),
                expectedResult = StyleType.AUTUMN
            ),
            TestObject(
                date = LocalDate.of(2023, 11, 17),
                expectedResult = StyleType.AUTUMN
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 6),
                expectedResult = StyleType.AUTUMN
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 22),
                expectedResult = StyleType.WINTER
            ),
        ).map {
            dynamicTest("When date is equal to ${it.date} then style provider returns ${it.expectedResult}") {
                every { currentDateProvider.provide() } returns it.date

                val result = styleProvider.provide()

                assertThat(result).isEqualTo(it.expectedResult)
            }
        }


    private data class TestObject(
        val date: LocalDate,
        val expectedResult: StyleType,
    )
}
