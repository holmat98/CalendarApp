package com.mateuszholik.dateutils

@JvmInline
value class Minutes private constructor(val value: Int) {

    companion object {
        private const val MINUTES_IN_DAY = 1440
        private const val MINUTES_IN_HOUR = 60
        fun from(minutes: Int): Minutes = Minutes(minutes)

        fun fromDays(days: Int): Minutes = Minutes(days * MINUTES_IN_DAY)

        fun fromHours(hours: Int): Minutes = Minutes(hours * MINUTES_IN_HOUR)
    }
}
