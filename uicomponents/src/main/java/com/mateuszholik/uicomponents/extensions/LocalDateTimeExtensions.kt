package com.mateuszholik.uicomponents.extensions

import java.time.LocalDateTime

internal fun LocalDateTime.asTimeString(): String =
    "$hour:$minute"
