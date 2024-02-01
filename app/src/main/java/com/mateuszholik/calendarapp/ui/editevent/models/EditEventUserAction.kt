package com.mateuszholik.calendarapp.ui.editevent.models

import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.domain.models.Calendar
import java.time.LocalDateTime

sealed class EditEventUserAction : UserAction {

    data object SelectCalendar : EditEventUserAction()

    data class CalendarSelected(val calendar: Calendar) : EditEventUserAction()

    data object CalendarSelectionDismissed : EditEventUserAction()

    data object ExitAttempt : EditEventUserAction()

    data object ExitAttemptConfirmed : EditEventUserAction()

    data object ExitAttemptCancelled : EditEventUserAction()

    data class UpdateDescription(val newDescription: String) : EditEventUserAction()

    data class UpdateLocation(val newLocation: String) : EditEventUserAction()

    data class UpdateTitle(val newTitle: String) : EditEventUserAction()

    data object SelectEventColor : EditEventUserAction()

    data class SelectedEventColor(val color: ColorsProvider.ColorInfo) : EditEventUserAction()

    data object SelectEventColorDismissed : EditEventUserAction()

    data class AllDaySelectionChanged(val allDay: Boolean) : EditEventUserAction()

    data class StartDateChanged(val newStartDate: LocalDateTime) : EditEventUserAction()

    data class EndDateChanged(val newEndDate: LocalDateTime) : EditEventUserAction()

    data object SaveUpdatedEventDetails : EditEventUserAction()
}
