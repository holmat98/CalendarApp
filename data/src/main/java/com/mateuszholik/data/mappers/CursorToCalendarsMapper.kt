package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory.Companion.CALENDAR_ACCOUNT_NAME_INDEX
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory.Companion.CALENDAR_ID_INDEX
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory.Companion.CALENDAR_NAME_INDEX
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory.Companion.CALENDAR_VISIBLE_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.Calendar
import javax.inject.Inject

internal interface CursorToCalendarsMapper : Mapper<Cursor, List<Calendar>>

internal class CursorToCalendarsMapperImpl @Inject constructor() : CursorToCalendarsMapper {

    override suspend fun map(param: Cursor): List<Calendar> =
        List(param.count) {
            param.moveToPosition(it)
            param.toCalendar()
        }

    private fun Cursor.toCalendar(): Calendar =
        Calendar(
            id = getLong(CALENDAR_ID_INDEX),
            calendarName = getString(CALENDAR_NAME_INDEX),
            accountName = getString(CALENDAR_ACCOUNT_NAME_INDEX),
            isVisible = getInt(CALENDAR_VISIBLE_INDEX) == 1
        )
}
