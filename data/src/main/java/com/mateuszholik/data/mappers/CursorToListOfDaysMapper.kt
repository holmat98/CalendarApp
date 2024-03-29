package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.DAYS_WITH_EVENTS_DATE_START_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.DAYS_WITH_EVENTS_TIMEZONE_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.dateutils.Milliseconds
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

internal interface CursorToListOfDaysMapper : Mapper<List<LocalDate>>

internal class CursorToListOfDaysMapperImpl @Inject constructor() : CursorToListOfDaysMapper {

    override suspend fun map(cursor: Cursor): List<LocalDate> =
        List(cursor.count) {
            cursor.moveToPosition(it)

            val dateStartMillis = cursor.getLong(DAYS_WITH_EVENTS_DATE_START_INDEX)
            val zoneId = cursor.getString(DAYS_WITH_EVENTS_TIMEZONE_INDEX)

            Milliseconds.ofMillis(dateStartMillis).asDate(ZoneId.of(zoneId))
        }
}
