package com.mateuszholik.data.mappers

import android.database.Cursor
import androidx.core.database.getIntOrNull
import com.mateuszholik.data.extensions.toLocalDateTime
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_ID_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_TITLE_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_DATE_START_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_DATE_END_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_COLOR_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_ALL_DAY_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_TIMEZONE_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.Event
import javax.inject.Inject

internal interface CursorToEventsMapper : Mapper<Cursor, List<Event>>

internal class CursorToEventsMapperImpl @Inject constructor() : CursorToEventsMapper {

    override suspend fun map(param: Cursor): List<Event> =
        List(param.count) {
            param.moveToPosition(it)
            param.toEvent()
        }.distinct()

    private fun Cursor.toEvent(): Event {
        val timezone = getString(TODAY_EVENTS_TIMEZONE_INDEX)

        return Event(
            id = getLong(TODAY_EVENTS_ID_INDEX),
            title = getString(TODAY_EVENTS_TITLE_INDEX),
            startDate = getLong(TODAY_EVENTS_DATE_START_INDEX).toLocalDateTime(timezone),
            endDate = getLong(TODAY_EVENTS_DATE_END_INDEX).toLocalDateTime(timezone),
            color = getIntOrNull(TODAY_EVENTS_COLOR_INDEX),
            allDay = getInt(TODAY_EVENTS_ALL_DAY_INDEX) == 1
        )
    }
}
