package com.mateuszholik.data.repositories.models

data class Attendee(
    val id: Long,
    val name: String,
    val email: String,
    val status: Int,
)
