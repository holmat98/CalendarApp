package com.mateuszholik.domain.extensions

import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.data.repositories.models.Calendar as DataCalendar
import com.mateuszholik.data.repositories.models.Event as DataEvent
import com.mateuszholik.domain.models.Event

internal fun DataEvent.toCommonModel(): Event =
    Event(
        id = id,
        title = title,
        startDate = startDate,
        endDate = endDate,
        color = color,
        allDay = allDay,
    )

internal fun DataCalendar.toCommonModel(): Calendar =
    Calendar(
        id = id,
        accountName = accountName,
        calendarName = calendarName,
        isVisible = isVisible
    )
