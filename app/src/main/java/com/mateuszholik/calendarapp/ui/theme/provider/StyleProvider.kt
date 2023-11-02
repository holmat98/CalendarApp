package com.mateuszholik.calendarapp.ui.theme.provider

import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import java.time.LocalDate
import javax.inject.Inject

interface StyleProvider {

    fun provide(): StyleType
}

class StyleProviderImpl @Inject constructor() : StyleProvider {

    override fun provide(): StyleType {
        val today = LocalDate.now()
        val currentYear = today.year

        return when (today) {
            in createSpringDateRange(currentYear) -> StyleType.SPRING
            in createSummerDateRange(currentYear) -> StyleType.SUMMER
            in createAutumnDateRange(currentYear) -> StyleType.AUTUMN
            else -> StyleType.WINTER
        }
    }

    private fun createSpringDateRange(year: Int): ClosedRange<LocalDate> {
        val firstDayOfSpring = LocalDate.of(year, FIRST_MONTH_OF_SPRING, FIRST_DAY_OF_SPRING)
        val lastDayOfSpring = LocalDate.of(year, LAST_MONTH_OF_SPRING, LAST_DAY_OF_SPRING)

        return firstDayOfSpring..lastDayOfSpring
    }

    private fun createSummerDateRange(year: Int): ClosedRange<LocalDate> {
        val firstDayOfSummer = LocalDate.of(year, FIRST_MONTH_OF_SUMMER, FIRST_DAY_OF_SUMMER)
        val lastDayOfSummer = LocalDate.of(year, LAST_MONTH_OF_SUMMER, LAST_DAY_OF_SUMMER)

        return firstDayOfSummer..lastDayOfSummer
    }

    private fun createAutumnDateRange(year: Int): ClosedRange<LocalDate> {
        val firstDayOfAutumn = LocalDate.of(year, FIRST_MONTH_OF_AUTUMN, FIRST_DAY_OF_AUTUMN)
        val lastDayOfAutumn = LocalDate.of(year, LAST_MONTH_OF_AUTUMN, LAST_DAY_OF_AUTUMN)

        return firstDayOfAutumn..lastDayOfAutumn
    }

    private companion object {
        const val FIRST_DAY_OF_SPRING = 20
        const val FIRST_MONTH_OF_SPRING = 3
        const val LAST_DAY_OF_SPRING = 20
        const val LAST_MONTH_OF_SPRING = 6
        const val FIRST_DAY_OF_SUMMER = 21
        const val FIRST_MONTH_OF_SUMMER = 6
        const val LAST_DAY_OF_SUMMER = 22
        const val LAST_MONTH_OF_SUMMER = 9
        const val FIRST_DAY_OF_AUTUMN = 23
        const val FIRST_MONTH_OF_AUTUMN = 9
        const val LAST_DAY_OF_AUTUMN = 20
        const val LAST_MONTH_OF_AUTUMN = 12
    }
}
