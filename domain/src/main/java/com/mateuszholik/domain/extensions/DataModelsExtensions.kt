package com.mateuszholik.domain.extensions

import com.mateuszholik.domain.models.Alert
import com.mateuszholik.domain.models.Attendee
import com.mateuszholik.domain.models.AttendeeStatus
import com.mateuszholik.domain.models.Availability
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.data.repositories.models.Alert as DataAlert
import com.mateuszholik.data.repositories.models.Attendee as DataAttendee
import com.mateuszholik.data.repositories.models.Calendar as DataCalendar
import com.mateuszholik.data.repositories.models.Event as DataEvent
import com.mateuszholik.data.repositories.models.EventDetails as DataEventDetails
import com.mateuszholik.domain.models.Event
import com.mateuszholik.domain.models.EventDetails

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

internal fun DataAlert.toCommonModel(): Alert =
    Alert(minutesBefore = minutesBefore)

internal fun DataAttendee.toCommonModel(): Attendee =
    Attendee(
        id = id,
        name = name,
        email = email,
        status = AttendeeStatus.entries.firstOrNull { it.value == status } ?: AttendeeStatus.NONE
    )

internal fun DataEventDetails.toCommonModel(
    attendees: List<Attendee>,
    alerts: List<Alert>,
): EventDetails =
    EventDetails(
        id = id,
        title = title,
        description = description,
        dateStart = dateStart,
        dateEnd = dateEnd,
        allDay = allDay,
        eventColor = eventColor,
        availability = Availability.entries.firstOrNull { it.value == availability } ?: Availability.FREE,
        location = location,
        organizer = organizer,
        canModify = canModify,
        alerts = alerts,
        attendees = attendees,
    )
