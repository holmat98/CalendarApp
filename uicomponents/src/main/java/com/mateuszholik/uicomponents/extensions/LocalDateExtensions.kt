package com.mateuszholik.uicomponents.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal fun LocalDate.asDayString(): String {
    val dateTimeFormatter = if (Locale.getDefault().country == Locale.US.country) {
        DateTimeFormatter.ofPattern("MMMM d yyyy")
    } else {
        DateTimeFormatter.ofPattern("d MMMM yyyy")
    }

    return this.format(dateTimeFormatter)
}
