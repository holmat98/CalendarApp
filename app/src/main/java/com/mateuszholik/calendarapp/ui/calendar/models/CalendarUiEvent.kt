package com.mateuszholik.calendarapp.ui.calendar.models

import com.mateuszholik.calendarapp.ui.base.UiEvent

sealed class CalendarUiEvent : UiEvent {

    data class NavigateToEvent(val eventId: Long) : CalendarUiEvent()

    data object NavigateToAddEvent : CalendarUiEvent()

    data object Error : CalendarUiEvent()

    data object NavigateToCalendarsSelection : CalendarUiEvent()
}
