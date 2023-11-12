package com.mateuszholik.calendarapp.ui.utils

import com.mateuszholik.domain.models.Event
import java.time.LocalDate
import java.time.LocalDateTime

internal object PreviewConstants {

    val CURRENT_DATE = LocalDate.of(2023, 11, 12)
    val DAYS_WITH_EVENTS = listOf(
        LocalDate.of(2023, 11, 1),
        LocalDate.of(2023, 11, 11),
        LocalDate.of(2023, 11, 12),
        LocalDate.of(2023, 11, 21),
        LocalDate.of(2023, 11, 30),
    )
    val EVENTS = listOf(
        Event(
            id = 0,
            title = "Event 1",
            startDate = LocalDateTime.of(2023, 11, 12, 9, 0),
            endDate = LocalDateTime.of(2023, 11, 12, 9, 30)
        ),
        Event(
            id = 1,
            title = "Event 2",
            startDate = LocalDateTime.of(2023, 11, 12, 12, 0),
            endDate = LocalDateTime.of(2023, 11, 12, 13, 0)
        ),
        Event(
            id = 2,
            title = "Event 3",
            startDate = LocalDateTime.of(2023, 11, 12, 14, 0),
            endDate = LocalDateTime.of(2023, 11, 12, 15, 30)
        ),
    )
}
