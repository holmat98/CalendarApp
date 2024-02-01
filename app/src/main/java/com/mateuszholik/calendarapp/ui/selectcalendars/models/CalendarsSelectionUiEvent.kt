package com.mateuszholik.calendarapp.ui.selectcalendars.models

import com.mateuszholik.calendarapp.ui.base.UiEvent

sealed class CalendarProfilesUiEvent : UiEvent {

    data object Error : CalendarProfilesUiEvent()

    data object CalendarsSelected : CalendarProfilesUiEvent()
}
