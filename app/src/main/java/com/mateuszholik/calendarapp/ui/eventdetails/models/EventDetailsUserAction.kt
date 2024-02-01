package com.mateuszholik.calendarapp.ui.eventdetails.models

import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.domain.models.Attendee

sealed class EventDetailsUserAction : UserAction {

    data class EditEventPressed(val eventId: Long) : EventDetailsUserAction()

    data object NavigateBack : EventDetailsUserAction()

    data object DeleteEvent : EventDetailsUserAction()

    data object DeleteEventConfirmed : EventDetailsUserAction()

    data object DeleteEventCancelled : EventDetailsUserAction()

    data class AttendeeSelected(val attendee: Attendee) : EventDetailsUserAction()

    data object AttendeeDismissed : EventDetailsUserAction()

    data object RetryGetEventDetailsPressed : EventDetailsUserAction()
}
