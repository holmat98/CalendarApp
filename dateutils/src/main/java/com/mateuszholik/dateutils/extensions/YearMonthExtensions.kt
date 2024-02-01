package com.mateuszholik.dateutils.extensions

import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

fun YearMonth.toText(): String {
    val monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())

    return "$monthName $year".capitalized()
}
