package com.mateuszholik.data.extensions

import android.database.Cursor
import com.mateuszholik.data.repositories.models.Event

internal fun <T> Cursor.toList(map: Cursor.() -> T): List<T> =
    List(count) {
        this.moveToPosition(it)
        this.map()
    }

internal fun Cursor.toEvent(): Event {
    val timezone = getString(4)

    return Event(
        id = getLong(0),
        title = getString(1),
        startDate = getLong(2).toLocalDateTime(timezone),
        endDate = getLong(3).toLocalDateTime(timezone)
    )
}
