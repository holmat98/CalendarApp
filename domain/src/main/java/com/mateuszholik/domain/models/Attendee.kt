package com.mateuszholik.domain.models

data class Attendee(
    val id: Long,
    val name: String,
    val email: String,
    val status: AttendeeStatus,
)
