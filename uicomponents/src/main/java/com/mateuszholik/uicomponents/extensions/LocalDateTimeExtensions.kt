package com.mateuszholik.uicomponents.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

internal fun LocalDateTime.asTimeString(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    return this.format(dateTimeFormatter)
}

internal fun LocalDateTime.asFullDayString(): String {
    val dayOfWeekName =
        dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()).capitalized()
    val shortMonthName = month.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    return "$dayOfWeekName, $dayOfMonth $shortMonthName $year"
}

internal fun LocalDateTime.asFullDayTimeString(): String =
    "${asFullDayString()} ${asTimeString()}"

internal fun LocalDateTime.asDayString(): String {
    val dateTimeFormatter = if (Locale.getDefault().country == Locale.US.country) {
        DateTimeFormatter.ofPattern("MMMM d yyyy")
    } else {
        DateTimeFormatter.ofPattern("d MMMM yyyy")
    }

    return this.format(dateTimeFormatter)
}

internal fun LocalDateTime.isSameDay(otherDay: LocalDateTime): Boolean =
    this.toLocalDate() == otherDay.toLocalDate()

internal fun LocalDateTime.copy(
    year: Int = this.year,
    month: Int = this.monthValue,
    dayOfMonth: Int = this.dayOfMonth,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
): LocalDateTime =
    LocalDateTime.of(year, month, dayOfMonth, hour, minute, second)
