package com.mateuszholik.uicomponents.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.stream.Stream

internal sealed class CalendarField {

    data class Day(val date: LocalDate) : CalendarField()

    data object Empty : CalendarField()

    companion object {
        fun createFieldsForMonth(yearMonth: YearMonth): List<CalendarField> {
            val result = mutableListOf<CalendarField>()

            val firstDayOfMonth = yearMonth.atDay(1)
            val nextMonthFirstDay = yearMonth.plusMonths(1).atDay(1)
            val lastDayOfMonth = nextMonthFirstDay.minusDays(1)

            if (firstDayOfMonth.dayOfWeek != DayOfWeek.MONDAY) {
                (1 until firstDayOfMonth.dayOfWeek.value).forEach { _ ->
                    result.add(Empty)
                }
            }

            firstDayOfMonth.daysUntil(nextMonthFirstDay).forEach {
                result.add(Day(it))
            }

            if (lastDayOfMonth.dayOfWeek != DayOfWeek.SUNDAY) {
                ((lastDayOfMonth.dayOfWeek.value + 1)..7).forEach { _ ->
                    result.add(Empty)
                }
            }

            return result
        }

        private fun LocalDate.daysUntil(endDate: LocalDate): Stream<LocalDate> =
            Stream
                .iterate(this) { it.plusDays(1) }
                .limit(ChronoUnit.DAYS.between(this, endDate))
    }
}
