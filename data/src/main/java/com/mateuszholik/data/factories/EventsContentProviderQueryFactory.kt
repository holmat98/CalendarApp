package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

internal interface EventsContentProviderQueryFactory {

    suspend fun createForEventsFromDay(day: LocalDate): QueryData

    suspend fun createForEventsFromMonth(yearMonth: YearMonth): QueryData
}

internal class EventsContentProviderQueryFactoryImpl @Inject constructor() :
    EventsContentProviderQueryFactory {

    override suspend fun createForEventsFromDay(day: LocalDate): QueryData {
        val dayAtStart = day.atStartOfDay()
        val dayAtEnd = day.plusDays(1).atStartOfDay()

        val zoneId = ZoneId.systemDefault()
        val dayAtStartEpochMillis = dayAtStart.atZone(zoneId).toInstant().toEpochMilli()
        val dayAtEndEpochMillis = dayAtEnd.atZone(zoneId).toInstant().toEpochMilli()

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?))"
        val selectionArgs = arrayOf("$dayAtStartEpochMillis", "$dayAtEndEpochMillis")
        val projection = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND,
            CalendarContract.Events.EVENT_TIMEZONE,
            CalendarContract.Events.EVENT_COLOR,
            CalendarContract.Events.ALL_DAY,
        )

        return QueryData(
            uri = CalendarContract.Events.CONTENT_URI,
            selection = selection,
            selectionArgs = selectionArgs,
            projection = projection
        )
    }

    override suspend fun createForEventsFromMonth(yearMonth: YearMonth): QueryData {
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

    companion object {
        internal const val TODAY_EVENTS_ID_INDEX = 0
        internal const val TODAY_EVENTS_TITLE_INDEX = 1
        internal const val TODAY_EVENTS_DATE_START_INDEX = 2
        internal const val TODAY_EVENTS_DATE_END_INDEX = 3
        internal const val TODAY_EVENTS_TIMEZONE_INDEX = 4
        internal const val TODAY_EVENTS_COLOR_INDEX = 5
        internal const val TODAY_EVENTS_ALL_DAY_INDEX = 6
        internal const val DAYS_WITH_EVENTS_DATE_START_INDEX = 0
        internal const val DAYS_WITH_EVENTS_TIMEZONE_INDEX = 1
    }
}
