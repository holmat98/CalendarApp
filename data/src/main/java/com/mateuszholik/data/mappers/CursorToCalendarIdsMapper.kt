package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory.Companion.CALENDAR_ID_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import javax.inject.Inject

internal interface CursorToCalendarIdsMapper : Mapper<List<Long>>

internal class CursorToCalendarIdsMapperImpl @Inject constructor() : CursorToCalendarIdsMapper {

    override suspend fun map(cursor: Cursor): List<Long> =
        List(cursor.count) {
            cursor.moveToPosition(it)
            cursor.getLong(CALENDAR_ID_INDEX)
        }
}
