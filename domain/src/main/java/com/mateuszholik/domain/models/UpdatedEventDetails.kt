package com.mateuszholik.domain.models

import java.time.LocalDateTime

data class UpdatedEventDetails(
    val id: Long,
    val title: String?,
    val description: String?,
    val dateStart: LocalDateTime?,
    val dateEnd: LocalDateTime?,
    val timezone: String,
    val allDay: Boolean?,
    val eventColor: Int?,
    val location: String?,
    val calendarId: Long?,
)
