package com.mateuszholik.data.extensions

import java.time.LocalDateTime
import java.time.ZoneOffset

internal fun LocalDateTime.toEpochMillis(): Long =
    toInstant(ZoneOffset.systemDefault().toZone()).toEpochMilli()
