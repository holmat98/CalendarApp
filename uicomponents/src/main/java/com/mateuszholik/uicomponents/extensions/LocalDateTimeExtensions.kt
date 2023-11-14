package com.mateuszholik.uicomponents.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal fun LocalDateTime.asTimeString(): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    return this.format(dateTimeFormatter)
}
