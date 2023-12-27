package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory.Companion.CALENDAR_ID_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import javax.inject.Inject

internal interface CursorToCalendarIdsMapper : Mapper<Cursor, List<Long>>

internal class CursorToCalendarIdsMapperImpl @Inject constructor() : CursorToCalendarIdsMapper {

    override suspend fun map(param: Cursor): List<Long> =
        List(param.count) {
            param.moveToPosition(it)
            param.getLong(CALENDAR_ID_INDEX)
        }
}
