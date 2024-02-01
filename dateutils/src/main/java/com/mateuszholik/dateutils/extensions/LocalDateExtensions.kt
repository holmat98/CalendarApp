package com.mateuszholik.dateutils.extensions

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.asDayString(): String {
    val dateTimeFormatter = if (Locale.getDefault().country == Locale.US.country) {
        DateTimeFormatter.ofPattern("MMMM d yyyy")
    } else {
        DateTimeFormatter.ofPattern("d MMMM yyyy")
    }

    return this.format(dateTimeFormatter)
}

fun LocalDate.toYearMonth(): YearMonth =
    YearMonth.of(year, month)
