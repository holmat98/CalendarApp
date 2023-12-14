package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.extensions.toLocalDate
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.DAYS_WITH_EVENTS_DATE_START_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.DAYS_WITH_EVENTS_TIMEZONE_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import java.time.LocalDate
import javax.inject.Inject

internal interface CursorToListOfDaysMapper: Mapper<Cursor, List<LocalDate>>

internal class CursorToListOfDaysMapperImpl @Inject constructor() : CursorToListOfDaysMapper {

    override suspend fun map(param: Cursor): List<LocalDate> =
        List(param.count) {
            param.moveToPosition(it)

            val dateStartMillis = param.getLong(DAYS_WITH_EVENTS_DATE_START_INDEX)
            val zoneId = param.getString(DAYS_WITH_EVENTS_TIMEZONE_INDEX)

            dateStartMillis.toLocalDate(zoneId)
        }
}
