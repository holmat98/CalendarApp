package com.mateuszholik.calendarapp.ui.permissions.calendar.models

import androidx.compose.runtime.Stable
import com.mateuszholik.calendarapp.ui.base.UiState

@Stable
sealed class CalendarPermissionUiState : UiState {

    data object Loading : CalendarPermissionUiState()

    data class AskForCalendarPermissions(
        val permissions: List<String>,
    ) : CalendarPermissionUiState()

    data class ShowRationaleForCalendarPermissions(
        val permissions: List<String>,
    ) : CalendarPermissionUiState()

    data object ShowSettings : CalendarPermissionUiState()
}
