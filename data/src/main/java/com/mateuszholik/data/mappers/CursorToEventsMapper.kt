package com.mateuszholik.data.mappers

import android.database.Cursor
import androidx.core.database.getIntOrNull
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_ID_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_TITLE_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_DATE_START_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_DATE_END_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_COLOR_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_ALL_DAY_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.TODAY_EVENTS_TIMEZONE_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.Event
import com.mateuszholik.dateutils.Milliseconds
import java.time.ZoneId
import javax.inject.Inject

internal interface CursorToEventsMapper : Mapper<List<Event>>

internal class CursorToEventsMapperImpl @Inject constructor() : CursorToEventsMapper {

    override suspend fun map(cursor: Cursor): List<Event> =
        List(cursor.count) {
            cursor.moveToPosition(it)
            cursor.toEvent()
        }.distinct()

    private fun Cursor.toEvent(): Event {
        val timezone = getString(TODAY_EVENTS_TIMEZONE_INDEX)

        return Event(
            id = getLong(TODAY_EVENTS_ID_INDEX),
            title = getString(TODAY_EVENTS_TITLE_INDEX),
            startDate = Milliseconds.ofMillis(getLong(TODAY_EVENTS_DATE_START_INDEX))
                .asDateTime(ZoneId.of(timezone)),
            endDate = Milliseconds.ofMillis(getLong(TODAY_EVENTS_DATE_END_INDEX))
                .asDateTime(ZoneId.of(timezone)),
            color = getIntOrNull(TODAY_EVENTS_COLOR_INDEX),
            allDay = getInt(TODAY_EVENTS_ALL_DAY_INDEX) == 1
        )
    }
}
