package com.mateuszholik.calendarapp.ui.utils

import com.mateuszholik.domain.models.Calendar
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
            startDate = LocalDateTime.of(2023, 11, 12, 0, 0),
            endDate = LocalDateTime.of(2023, 11, 13, 0, 0),
            allDay = true,
            color = null,
        ),
        Event(
            id = 1,
            title = "Event 2",
            startDate = LocalDateTime.of(2023, 11, 12, 12, 0),
            endDate = LocalDateTime.of(2023, 11, 12, 13, 0),
            allDay = false,
            color = null,
        ),
        Event(
            id = 2,
            title = "Event 3",
            startDate = LocalDateTime.of(2023, 11, 12, 14, 0),
            endDate = LocalDateTime.of(2023, 11, 12, 15, 30),
            allDay = false,
            color = null,
        ),
    )
    val CALENDAR_1 = Calendar(
        id = 1,
        accountName = "Account 1",
        calendarName = "Calendar 1",
        isVisible = true
    )
    val CALENDAR_2 = Calendar(
        id = 2,
        accountName = "Account 1",
        calendarName = "Calendar 2",
        isVisible = false
    )
    val CALENDAR_3 = Calendar(
        id = 3,
        accountName = "Account 2",
        calendarName = "Calendar 3",
        isVisible = false
    )
    val CALENDAR_4 = Calendar(
        id = 4,
        accountName = "Account 2",
        calendarName = "Calendar 4",
        isVisible = true
    )
    val CALENDARS = mapOf(
        "Account 1" to listOf(CALENDAR_1, CALENDAR_2),
        "Account 2" to listOf(CALENDAR_3, CALENDAR_4),
    )
}
