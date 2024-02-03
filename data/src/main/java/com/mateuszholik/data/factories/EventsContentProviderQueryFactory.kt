package com.mateuszholik.data.factories

import android.content.ContentUris
import android.content.ContentValues
import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.DeleteData
import com.mateuszholik.data.factories.models.QueryData
import com.mateuszholik.data.factories.models.UpdateData
import com.mateuszholik.data.repositories.models.UpdatedEventDetails
import com.mateuszholik.dateutils.Milliseconds
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

internal interface EventsContentProviderQueryFactory {

    suspend fun createForEventsFromDay(day: LocalDate, calendarIds: List<Long>): QueryData

    suspend fun createForEvent(eventId: Long): QueryData

    suspend fun createForEventsFromMonth(yearMonth: YearMonth, calendarIds: List<Long>): QueryData

    suspend fun createForUpdateEvent(updatedEventDetails: UpdatedEventDetails): UpdateData

    suspend fun createForDeleteEvent(eventId: Long): DeleteData

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
        const val EVENT_ID_INDEX = 0
        const val EVENT_TITLE_INDEX = 1
        const val EVENT_DESCRIPTION_INDEX = 2
        const val EVENT_DATE_START_INDEX = 3
        const val EVENT_DATE_END_INDEX = 4
        const val EVENT_TIMEZONE_INDEX = 5
        const val EVENT_ALL_DAY_INDEX = 6
        const val EVENT_COLOR_INDEX = 7
        const val EVENT_AVAILABILITY_INDEX = 8
        const val EVENT_LOCATION_INDEX = 9
        const val EVENT_ORGANIZER_INDEX = 10
        const val EVENT_HAS_ALARM_INDEX = 11
        const val EVENT_IS_ORGANIZER_INDEX = 12
        const val EVENT_CAN_MODIFY_INDEX = 13
        const val EVENT_CAN_SEE_GUESTS_INDEX = 14
        const val EVENT_CALENDAR_ID_INDEX = 15
    }
}

internal class EventsContentProviderQueryFactoryImpl @Inject constructor() :
    EventsContentProviderQueryFactory {

    override suspend fun createForEventsFromDay(
        day: LocalDate,
        calendarIds: List<Long>,
    ): QueryData {
        val dayAtStartEpochMillis = Milliseconds.ofDate(day)
        val dayAtEndEpochMillis = Milliseconds.ofDate(day.plusDays(1))

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?) ${calendarIds.asInSelection()})"

        val selectionArgs = arrayOf(
            "${dayAtStartEpochMillis.value}",
            "${dayAtEndEpochMillis.value}",
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

    override suspend fun createForEvent(eventId: Long): QueryData =
        QueryData(
            uri = CalendarContract.Events.CONTENT_URI,
            projection = arrayOf(
                CalendarContract.Events._ID,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
                CalendarContract.Events.EVENT_TIMEZONE,
                CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.EVENT_COLOR,
                CalendarContract.Events.AVAILABILITY,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.ORGANIZER,
                CalendarContract.Events.HAS_ALARM,
                CalendarContract.Events.IS_ORGANIZER,
                CalendarContract.Events.GUESTS_CAN_MODIFY,
                CalendarContract.Events.GUESTS_CAN_SEE_GUESTS,
                CalendarContract.Events.CALENDAR_ID,
            ),
            selection = "(${CalendarContract.Events._ID} = ?)",
            selectionArgs = arrayOf("$eventId")
        )

    override suspend fun createForEventsFromMonth(
        yearMonth: YearMonth,
        calendarIds: List<Long>,
    ): QueryData {
        val dayAtStartEpochMillis = Milliseconds.ofDate(yearMonth.atDay(1))
        val dayAtEndEpochMillis = Milliseconds.ofDate(yearMonth.plusMonths(1).atDay(1))

        val selection =
            "((${CalendarContract.Events.DTSTART} >= ?) AND (${CalendarContract.Events.DTSTART} < ?) ${calendarIds.asInSelection()})"
        val selectionArgs = arrayOf(
            "${dayAtStartEpochMillis.value}",
            "${dayAtEndEpochMillis.value}"
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

    override suspend fun createForUpdateEvent(updatedEventDetails: UpdatedEventDetails): UpdateData =
        UpdateData(
            uri = ContentUris.withAppendedId(
                CalendarContract.Events.CONTENT_URI,
                updatedEventDetails.id
            ),
            values = ContentValues().apply {
                with(updatedEventDetails) {
                    title?.let { put(CalendarContract.Events.TITLE, it) }
                    description?.let { put(CalendarContract.Events.DESCRIPTION, it) }
                    dateStart?.let {
                        put(
                            CalendarContract.Events.DTSTART,
                            Milliseconds.ofDateTime(it, ZoneId.of(timezone)).value
                        )
                    }
                    dateEnd?.let {
                        put(
                            CalendarContract.Events.DTEND,
                            Milliseconds.ofDateTime(it, ZoneId.of(timezone)).value
                        )
                    }
                    allDay?.let { put(CalendarContract.Events.ALL_DAY, it) }
                    eventColor?.let { put(CalendarContract.Events.EVENT_COLOR, it) }
                    location?.let { put(CalendarContract.Events.EVENT_LOCATION, it) }
                    calendarId?.let { put(CalendarContract.Events.CALENDAR_ID, it) }
                }
            }
        )

    override suspend fun createForDeleteEvent(eventId: Long): DeleteData =
        DeleteData(
            uri = ContentUris.withAppendedId(
                CalendarContract.Events.CONTENT_URI,
                eventId
            )
        )

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
