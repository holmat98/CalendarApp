package com.mateuszholik.data.extensions

import android.database.Cursor
import com.mateuszholik.data.repositories.models.Event

internal fun <T> Cursor.toList(map: Cursor.() -> T): List<T> =
    List(count) {
        this.moveToPosition(it)
        this.map()
    }

internal fun Cursor.toEvent(): Event =
    Event(
        id = getLong(0),
        title = getString(1),
        startDate = getLong(2).toLocalDateTime(),
        endDate = getLong(3).toLocalDateTime()
    )
