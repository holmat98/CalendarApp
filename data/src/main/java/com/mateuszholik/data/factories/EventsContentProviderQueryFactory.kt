package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

internal interface EventsContentProviderQueryFactory {

    suspend fun createForEventsFromDay(day: LocalDate, calendarIds: List<Long>): QueryData

    suspend fun createForEventsFromMonth(yearMonth: YearMonth, calendarIds: List<Long>): QueryData

    companion object {
        const val TODAY_EVENTS_ID_INDEX = 0
        const val TODAY_EVENTS_TITLE_INDEX = 1
        const val TODAY_EVENTS_DATE_START_INDEX = 2
        const val TODAY_EVENTS_DATE_END_INDEX = 3
        const val TODAY_EVENTS_TIMEZONE_INDEX = 4
        const val TODAY_EVENTS_COLOR_INDEX = 5
        const val TODAY_EVENTS_ALL_DAY_INDEX = 6
        const val DAYS_WITH_EVENTS_DATE_START_INDEX = 0
        const val DAYS_WITH_EVENTS_TIMEZONE_INDEX = 1
    }
}

internal class EventsContentProviderQueryFactoryImpl @Inject constructor() :
    EventsContentProviderQueryFactory {

    override suspend fun createForEventsFromDay(
        day: LocalDate,
        calendarIds: List<Long>,
    ): QueryData {
        val dayAtStart = day.atStartOfDay()
        val dayAtEnd = day.plusDays(1).atStartOfDay()

        val zoneId = ZoneId.systemDefault()
        val dayAtStartEpochMillis = dayAtStart.atZone(zoneId).toInstant().toEpochMilli()
        val dayAtEndEpochMillis = dayAtEnd.atZone(zoneId).toInstant().toEpochMilli()

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?) ${calendarIds.asInSelection()})"

        val selectionArgs = arrayOf(
            "$dayAtStartEpochMillis",
            "$dayAtEndEpochMillis",
        ) + calendarIds.map { "$it" }.toTypedArray()

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

    override suspend fun createForEventsFromMonth(
        yearMonth: YearMonth,
        calendarIds: List<Long>,
    ): QueryData {
        val firstDay = yearMonth.atDay(1).atStartOfDay()
        val lastDay = yearMonth.plusMonths(1).atDay(1).atStartOfDay()

        val zoneId = ZoneId.systemDefault()
        val dayAtStartEpochMillis = firstDay.atZone(zoneId).toInstant().toEpochMilli()
        val dayAtEndEpochMillis = lastDay.atZone(zoneId).toInstant().toEpochMilli()

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?) ${calendarIds.asInSelection()})"
        val selectionArgs = arrayOf(
            "$dayAtStartEpochMillis",
            "$dayAtEndEpochMillis"
        ) + calendarIds.map { "$it" }.toTypedArray()
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

    private fun List<Long>.asInSelection(): String {
        if (isEmpty()) {
            return ""
        }

        return StringBuilder().apply {
            append("AND (${CalendarContract.Events.CALENDAR_ID} IN (")
            this@asInSelection.forEachIndexed { index, _ ->
                append("?")
                if (index != this@asInSelection.lastIndex) {
                    append(",")
                }
            }
            append("))")
        }.toString()
    }
}
