package com.mateuszholik.data.extensions

import java.time.LocalDateTime
import java.time.ZoneId

internal fun LocalDateTime.toMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long =
    atZone(zoneId).toInstant().toEpochMilli()
