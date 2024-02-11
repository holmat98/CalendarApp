package com.mateuszholik.calendarapp.extensions

import java.util.TimeZone

internal fun TimeZone.asText(): String =
    "$id - $displayName"
