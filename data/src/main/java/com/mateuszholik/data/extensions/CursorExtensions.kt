package com.mateuszholik.data.extensions

import android.database.Cursor
import androidx.core.database.getIntOrNull
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_ALL_DAY_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_COLOR_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_DATE_END_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_DATE_START_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_ID_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_TIMEZONE_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl.Companion.TODAY_EVENTS_TITLE_INDEX
import com.mateuszholik.data.repositories.models.Event

internal fun <T> Cursor.toList(map: Cursor.() -> T): List<T> =
    List(count) {
        this.moveToPosition(it)
        this.map()
    }

internal fun Cursor.toEvent(): Event {
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
