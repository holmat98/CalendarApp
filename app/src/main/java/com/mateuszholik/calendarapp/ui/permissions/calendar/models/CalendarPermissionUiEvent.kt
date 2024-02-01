package com.mateuszholik.calendarapp.ui.permissions.calendar.models

import com.mateuszholik.calendarapp.ui.base.UiEvent

sealed class CalendarPermissionUiEvent : UiEvent {

    data object AllPermissionsGranted : CalendarPermissionUiEvent()

    data object Error : CalendarPermissionUiEvent()
}
