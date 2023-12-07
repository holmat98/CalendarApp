package com.mateuszholik.domain.models

data class Calendar(
    val id: Long,
    val accountName: String,
    val calendarName: String,
    val isVisible: Boolean,
)
