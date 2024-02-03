package com.mateuszholik.calendarapp.ui.eventdetails.models

import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.domain.models.Attendee

sealed class EventDetailsUiEvent : UiEvent {

    data object DismissAttendee : EventDetailsUiEvent()

    data object DismissDeleteEventConfirmation : EventDetailsUiEvent()

    data object Error : EventDetailsUiEvent()

    data class GoToEventDetails(val eventId: Long) : EventDetailsUiEvent()

    data object NavigateBack : EventDetailsUiEvent()

    data class ShowAttendee(val attendee: Attendee) : EventDetailsUiEvent()

    data object ShowDeleteEventConfirmation : EventDetailsUiEvent()
}
