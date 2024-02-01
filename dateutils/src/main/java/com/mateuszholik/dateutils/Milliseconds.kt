package com.mateuszholik.dateutils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@JvmInline
value class Milliseconds private constructor(val value: Long) {

    fun asDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime =
        Instant.ofEpochMilli(value).atZone(zoneId).toLocalDateTime()

    fun asDate(zoneId: ZoneId = ZoneId.systemDefault()): LocalDate =
        Instant.ofEpochMilli(value).atZone(zoneId).toLocalDate()

    companion object {
        fun ofDateTime(
            dateTime: LocalDateTime,
            zoneId: ZoneId = ZoneId.systemDefault(),
        ): Milliseconds =
            Milliseconds(dateTime.atZone(zoneId).toInstant().toEpochMilli())

        fun ofDate(
            date: LocalDate,
        ): Milliseconds =
            Milliseconds(
                date.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )

        fun ofMillis(milliseconds: Long): Milliseconds =
            Milliseconds(milliseconds)

        fun ofSeconds(seconds: Seconds): Milliseconds =
            Milliseconds(seconds.value.times(1000))
    }
}
