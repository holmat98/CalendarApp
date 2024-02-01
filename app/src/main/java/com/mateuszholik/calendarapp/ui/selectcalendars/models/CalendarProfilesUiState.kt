package com.mateuszholik.calendarapp.ui.selectcalendars.models

import androidx.compose.runtime.Stable
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.domain.models.Calendar

@Stable
sealed class CalendarProfilesUiState : UiState {

    data object Loading : CalendarProfilesUiState()

    data class Calendars(val calendars: Map<String, List<Calendar>>) : CalendarProfilesUiState()
}
