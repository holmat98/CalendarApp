package com.mateuszholik.calendarapp.ui.provider

import java.time.ZoneId
import java.util.TimeZone
import javax.inject.Inject

interface TimezoneProvider {

    fun provideAllTimezones(): List<TimeZone>

    fun provideDefault(): TimeZone

    fun provideUTC(): TimeZone
}

internal class TimezoneProviderImpl @Inject constructor() : TimezoneProvider {

    override fun provideAllTimezones(): List<TimeZone> =
        TimeZone.getAvailableIDs()
            .map { TimeZone.getTimeZone(it) }
            .filterNot { it.id.contains("Etc", true) }
            .sortedBy { it.id }

    override fun provideDefault(): TimeZone = TimeZone.getDefault()

    override fun provideUTC(): TimeZone = TimeZone.getTimeZone(ZoneId.of("Etc/UTC"))
}
