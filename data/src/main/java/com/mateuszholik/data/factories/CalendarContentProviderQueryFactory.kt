package com.mateuszholik.data.factories

import android.content.ContentUris
import android.content.ContentValues
import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import com.mateuszholik.data.factories.models.UpdateData
import javax.inject.Inject

internal interface CalendarContentProviderQueryFactory {

    suspend fun createForCalendars(): QueryData

    suspend fun createForCalendarWith(id: Long): QueryData

    suspend fun createForSelectedCalendarsIds(): QueryData

    suspend fun createForUpdateCalendarVisibility(
        calendarId: Long,
        isVisible: Boolean,
    ): UpdateData

    companion object {
        const val CALENDAR_ID_INDEX = 0
        const val CALENDAR_NAME_INDEX = 1
        const val CALENDAR_ACCOUNT_NAME_INDEX = 2
        const val CALENDAR_VISIBLE_INDEX = 3
        const val CALENDAR_COLOR_INDEX = 4
    }
}

internal class CalendarContentProviderQueryFactoryImpl @Inject constructor() :
    CalendarContentProviderQueryFactory {

    override suspend fun createForCalendars(): QueryData {
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.CALENDAR_COLOR,
        )

        return QueryData(
            uri = CalendarContract.Calendars.CONTENT_URI,
            projection = projection,
            selection = null,
            selectionArgs = null
        )
    }

    override suspend fun createForCalendarWith(id: Long): QueryData {
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.CALENDAR_COLOR,
        )

        return QueryData(
            uri = CalendarContract.Calendars.CONTENT_URI,
            projection = projection,
            selection = "(${CalendarContract.Calendars._ID} = ?)",
            selectionArgs = arrayOf("$id")
        )
    }

    override suspend fun createForSelectedCalendarsIds(): QueryData =
        QueryData(
            uri = CalendarContract.Calendars.CONTENT_URI,
            projection = arrayOf(CalendarContract.Calendars._ID),
            selection = "(${CalendarContract.Calendars.VISIBLE} = 1)",
            selectionArgs = null
        )

    override suspend fun createForUpdateCalendarVisibility(
        calendarId: Long,
        isVisible: Boolean,
    ): UpdateData = UpdateData(
        uri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calendarId),
        values = ContentValues().apply {
            put(CalendarContract.Calendars.VISIBLE, isVisible.asInt())
        }
    )

    private fun Boolean.asInt(): Int =
        if (this) {
            1
        } else {
            0
        }
}
