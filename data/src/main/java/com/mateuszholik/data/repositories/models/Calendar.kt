package com.mateuszholik.data.repositories.models

data class Calendar(
    val id: Long,
    val accountName: String,
    val calendarName: String,
    val isVisible: Boolean,
    val color: Int?,
)
