package com.mateuszholik.data.repositories.models

import java.time.LocalDateTime

data class NewEvent(
    val title: String,
    val description: String,
    val allDay: Boolean,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val timezone: String,
    val calendarId: Long,
    val eventColor: Int,
    val location: String,
    val reminder: Int?,
)
