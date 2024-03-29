package com.mateuszholik.calendarapp.ui.eventdetails.models

import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.domain.models.Attendee

sealed class EventDetailsUserAction : UserAction {

    data object AttendeeDismissed : EventDetailsUserAction()

    data class AttendeeSelected(val attendee: Attendee) : EventDetailsUserAction()

    data object DeleteEvent : EventDetailsUserAction()

    data object DeleteEventCancelled : EventDetailsUserAction()

    data object DeleteEventConfirmed : EventDetailsUserAction()

    data class EditEventPressed(val eventId: Long) : EventDetailsUserAction()

    data class LaunchEmail(val email: String) : EventDetailsUserAction()

    data class LaunchMaps(val location: String) : EventDetailsUserAction()

    data class LaunchUrl(val url: String) : EventDetailsUserAction()

    data object NavigateBackPressed : EventDetailsUserAction()

    data object RetryGetEventDetailsPressed : EventDetailsUserAction()
}
