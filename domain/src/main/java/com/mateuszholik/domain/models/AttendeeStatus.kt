package com.mateuszholik.domain.models

enum class AttendeeStatus(internal val value: Int) {
    NONE(0),
    ACCEPTED(1),
    DECLINED(2),
    INVITED(3),
    TENTATIVE(4),
}
