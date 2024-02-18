package com.mateuszholik.domain.models

import java.time.LocalDateTime

data class NewEvent(
    val title: String,
    val description: CalendarAppDescription,
    val allDay: Boolean,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val timezone: String,
    val calendarId: Long,
    val eventColor: Int,
    val location: String,
    val reminder: Int?,
)
