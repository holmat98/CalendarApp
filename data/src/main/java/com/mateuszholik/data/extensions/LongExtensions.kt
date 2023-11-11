package com.mateuszholik.data.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

internal fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault().toZone()
    )

internal fun Long.toLocalDate(): LocalDate =
    toLocalDateTime().toLocalDate()
