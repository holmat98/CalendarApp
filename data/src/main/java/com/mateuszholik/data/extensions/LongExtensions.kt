package com.mateuszholik.data.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

internal fun Long.toLocalDateTime(zoneId: String): LocalDateTime =
    Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.of(zoneId))
        .toLocalDateTime()

internal fun Long.toLocalDate(zoneId: String): LocalDate =
    toLocalDateTime(zoneId).toLocalDate()
