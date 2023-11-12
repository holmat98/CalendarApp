package com.mateuszholik.data.repositories.models

import java.time.LocalDateTime

data class Event(
    val id: Long,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
