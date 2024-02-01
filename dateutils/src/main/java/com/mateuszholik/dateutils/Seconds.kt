package com.mateuszholik.dateutils

import java.time.LocalDateTime
import java.time.ZoneOffset

@JvmInline
value class Seconds private constructor(val value: Long) {

    companion object {

        fun ofDateTime(dateTime: LocalDateTime): Seconds =
            Seconds(dateTime.toEpochSecond(ZoneOffset.UTC))
    }
}
