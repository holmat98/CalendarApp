package com.mateuszholik.data.extensions

import java.time.ZoneId
import java.time.ZoneOffset

internal fun ZoneId.toZone(): ZoneOffset =
    ZoneOffset.of(id)
