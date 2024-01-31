package com.mateuszholik.data.mappers

import android.database.Cursor
import androidx.core.database.getIntOrNull
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.Calendar
import javax.inject.Inject

internal interface CursorToCalendarMapper : Mapper<Calendar>

internal class CursorToCalendarMapperImpl @Inject constructor() : CursorToCalendarMapper {

    override suspend fun map(cursor: Cursor): Calendar =
        cursor.run {
            moveToFirst()
            toCalendar()
        }


    private fun Cursor.toCalendar(): Calendar =
        Calendar(
            id = getLong(CalendarContentProviderQueryFactory.CALENDAR_ID_INDEX),
            calendarName = getString(CalendarContentProviderQueryFactory.CALENDAR_NAME_INDEX),
            accountName = getString(CalendarContentProviderQueryFactory.CALENDAR_ACCOUNT_NAME_INDEX),
            isVisible = getInt(CalendarContentProviderQueryFactory.CALENDAR_VISIBLE_INDEX) == 1,
            color = getIntOrNull(CalendarContentProviderQueryFactory.CALENDAR_COLOR_INDEX)
        )
}
