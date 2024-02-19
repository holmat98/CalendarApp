package com.mateuszholik.calendarapp.extensions

import android.content.Context
import com.mateuszholik.calendarapp.R
import com.mateuszholik.dateutils.Minutes

internal fun Minutes.asText(context: Context): String =
    when {
        value >= 1440 -> {
            val days = value / 1440
            val weeks = (days % 7) + 1

            if (weeks == 1) {
                context.resources.getQuantityString(R.plurals.reminder_weeks_before, weeks, weeks)
            } else {
                context.resources.getQuantityString(R.plurals.reminder_days_before, days, days)
            }
        }
        value >= 60 -> {
            val hours = value / 60
            context.resources.getQuantityString(R.plurals.reminder_hours_before, hours, hours)
        }
        else -> context.getString(R.string.reminder_minutes_before, value)
    }
