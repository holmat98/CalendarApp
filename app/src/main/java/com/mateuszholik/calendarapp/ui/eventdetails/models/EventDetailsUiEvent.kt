package com.mateuszholik.calendarapp.ui.eventdetails.models

import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.domain.models.Attendee

sealed class EventDetailsUiEvent : UiEvent {

    data object Error : EventDetailsUiEvent()

    data class ShowAttendee(val attendee: Attendee) : EventDetailsUiEvent()

    data object DismissAttendee : EventDetailsUiEvent()

    data class GoToEventDetails(val eventId: Long) : EventDetailsUiEvent()
}
