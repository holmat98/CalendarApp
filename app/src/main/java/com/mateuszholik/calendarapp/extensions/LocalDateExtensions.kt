package com.mateuszholik.calendarapp.extensions

import java.time.LocalDate
import java.time.YearMonth

internal fun LocalDate.toYearMonth(): YearMonth =
    YearMonth.of(year, month)
