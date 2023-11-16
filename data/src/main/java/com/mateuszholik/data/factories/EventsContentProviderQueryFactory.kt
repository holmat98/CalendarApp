package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

internal interface EventsContentProviderQueryFactory {

    fun createForEventsFromDay(day: LocalDate): QueryData

    fun createForEventsFromMonth(yearMonth: YearMonth): QueryData
}

internal class EventsContentProviderQueryFactoryImpl @Inject constructor() :
    EventsContentProviderQueryFactory {

    override fun createForEventsFromDay(day: LocalDate): QueryData {
        val dayAtStart = day.atStartOfDay()
        val dayAtEnd = day.plusDays(1).atStartOfDay()

        val zoneId = ZoneId.systemDefault()
        val dayAtStartEpochMillis = dayAtStart.atZone(zoneId).toInstant().toEpochMilli()
        val dayAtEndEpochMillis = dayAtEnd.atZone(zoneId).toInstant().toEpochMilli()

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?))"
        val selectionArgs = arrayOf("$dayAtStartEpochMillis", "$dayAtEndEpochMillis")
        val projection = arrayOf(
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_TIMEZONE
        )

        return QueryData(
            uri = CalendarContract.Events.CONTENT_URI,
            selection = selection,
            selectionArgs = selectionArgs,
            projection = projection
        )
    }

    override fun createForEventsFromMonth(yearMonth: YearMonth): QueryData {
        val firstDay = yearMonth.atDay(1).atStartOfDay()
        val lastDay = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        val zoneId = ZoneId.systemDefault()
        val dayAtStartEpochMillis = firstDay.atZone(zoneId).toInstant().toEpochMilli()
        val dayAtEndEpochMillis = lastDay.atZone(zoneId).toInstant().toEpochMilli()

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?))"
        val selectionArgs = arrayOf("$dayAtStartEpochMillis", "$dayAtEndEpochMillis")
        val projection = arrayOf(
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.EVENT_TIMEZONE
        )

        return QueryData(
            uri = CalendarContract.Events.CONTENT_URI,
            selection = selection,
            selectionArgs = selectionArgs,
            projection = projection
        )
    }
}
