package com.mateuszholik.domain.models

enum class Availability(internal val value: Int) {
    BUSY(0),
    FREE(1),
    TENTATIVE(2)
}
