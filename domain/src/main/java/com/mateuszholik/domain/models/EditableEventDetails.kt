package com.mateuszholik.domain.models

import java.time.LocalDateTime

data class EditableEventDetails(
    val id: Long,
    val title: String,
    val description: Description,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime,
    val allDay: Boolean,
    val eventColor: Int?,
    val location: String,
    val calendar: Calendar?,
)
