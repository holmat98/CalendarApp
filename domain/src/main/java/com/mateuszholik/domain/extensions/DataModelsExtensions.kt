package com.mateuszholik.domain.extensions

import com.mateuszholik.data.repositories.models.Event as DataEvent
import com.mateuszholik.domain.models.Event

internal fun List<DataEvent>.toCommonModelList(): List<Event> =
    this.map { it.toCommonModel() }

internal fun DataEvent.toCommonModel(): Event =
    Event(
        id = id,
        title = title,
        startDate = startDate,
        endDate = endDate,
        color = color,
        allDay = allDay,
    )
