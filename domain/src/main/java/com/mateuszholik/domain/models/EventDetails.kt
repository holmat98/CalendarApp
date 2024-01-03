package com.mateuszholik.domain.models

import java.time.LocalDateTime

data class EventDetails(
    val id: Long,
    val title: String,
    val description: Description,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime,
    val allDay: Boolean,
    val eventColor: Int?,
    val availability: Availability,
    val location: String,
    val organizer: String,
    val canModify: Boolean,
    val alerts: List<Alert>,
    val attendees: List<Attendee>,
)
