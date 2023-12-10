package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import javax.inject.Inject

internal interface CalendarContentProviderQueryFactory {

    suspend fun createForCalendars(): QueryData

    companion object {
        const val CALENDAR_ID_INDEX = 0
        const val CALENDAR_NAME_INDEX = 1
        const val CALENDAR_ACCOUNT_NAME_INDEX = 2
        const val CALENDAR_VISIBLE_INDEX = 3
    }
}

internal class CalendarContentProviderQueryFactoryImpl @Inject constructor() :
    CalendarContentProviderQueryFactory {

    override suspend fun createForCalendars(): QueryData {
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.VISIBLE
        )

        return QueryData(
            uri = CalendarContract.Calendars.CONTENT_URI,
            projection = projection,
            selection = null,
            selectionArgs = null
        )
    }
}
