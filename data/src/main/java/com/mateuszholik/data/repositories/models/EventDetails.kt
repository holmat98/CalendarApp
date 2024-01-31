package com.mateuszholik.data.repositories.models

import java.time.LocalDateTime

data class EventDetails(
    val id: Long,
    val title: String,
    val description: String,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime,
    val timezone: String,
    val allDay: Boolean,
    val eventColor: Int?,
    val availability: Int,
    val location: String,
    val organizer: String,
    val hasAlarm: Boolean,
    val canModify: Boolean,
    val canSeeGuests: Boolean,
    val calendarId: Long,
)
