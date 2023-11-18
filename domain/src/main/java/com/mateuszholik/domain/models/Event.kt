package com.mateuszholik.domain.models

import java.time.LocalDateTime

data class Event(
    val id: Long,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val color: Int?,
    val allDay: Boolean,
)
