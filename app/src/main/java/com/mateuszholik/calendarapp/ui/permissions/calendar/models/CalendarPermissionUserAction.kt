package com.mateuszholik.calendarapp.ui.permissions.calendar.models

import com.mateuszholik.calendarapp.ui.base.UserAction

sealed class CalendarPermissionUserAction : UserAction {

    data class OnCalendarPermissionResultUserAction(
        val results: Map<String, Boolean>,
    ) : CalendarPermissionUserAction()

    data object OnReturnBackFromSettingsUserAction : CalendarPermissionUserAction()
}
