package com.mateuszholik.calendarapp.ui.selectcalendars.models

import com.mateuszholik.calendarapp.ui.base.UserAction

sealed class CalendarProfilesUserAction : UserAction {

    data class OnCalendarSelectionChanged(
        val calendarId: Long,
        val isVisible: Boolean,
    ) : CalendarProfilesUserAction()

    data object OnCalendarsConfirmed : CalendarProfilesUserAction()
}
