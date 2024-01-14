package com.mateuszholik.calendarapp.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mateuszholik.domain.models.Alert
import com.mateuszholik.domain.models.Attendee
import com.mateuszholik.domain.models.AttendeeStatus
import com.mateuszholik.domain.models.Availability
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Event
import com.mateuszholik.domain.models.EventDetails
import com.mateuszholik.domain.models.Generic
import com.mateuszholik.domain.models.GoogleMeet
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
    val EVENT_DETAILS = EventDetails(
        id = 1L,
        title = "Event title",
        description = GoogleMeet(
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
            originalDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis.",
            meetingUrl = "https://meet.google.com",
            otherUrls = listOf(
                "https://www.youtube.com",
                "https://www.facebook.com"
            )
        ),
        alerts = listOf(Alert("30")),
        allDay = false,
        dateStart = LocalDateTime.of(2023, 12, 31, 12, 0, 0),
        dateEnd = LocalDateTime.of(2023, 12, 31, 13, 30, 0),
        attendees = listOf(
            Attendee(
                1,
                name = "Attendee 1",
                email = "email",
                status = AttendeeStatus.ACCEPTED
            ),
            Attendee(
                2,
                name = "Attendee 2",
                email = "email",
                status = AttendeeStatus.DECLINED
            ),
            Attendee(
                3,
                name = "Attendee 3",
                email = "email",
                status = AttendeeStatus.INVITED
            ),
        ),
        availability = Availability.FREE,
        canModify = true,
        eventColor = Color.Green.toArgb(),
        location = "Zabrze",
        organizer = "Organizer",
    )
    val EVENT_DETAILS_GENERIC_DESCRIPTION = EVENT_DETAILS.copy(
        description = Generic(
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec mollis erat in libero posuere mattis. Aenean dapibus risus consequat, faucibus est at, vestibulum ipsum. Phasellus a elit id nisl euismod rhoncus. Aenean accumsan eget ante et dignissim. Proin non purus vel lacus facilisis facilisis."
        )
    )
    val EVENT_DETAILS_EMPTY_DESCRIPTION = EVENT_DETAILS.copy(
        description = Generic("")
    )
}
