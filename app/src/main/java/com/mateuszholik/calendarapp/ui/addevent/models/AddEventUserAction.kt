package com.mateuszholik.calendarapp.ui.addevent.models

import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.domain.models.Calendar
import java.time.LocalDateTime
import java.util.TimeZone

sealed class AddEventUserAction : UserAction {

    data class AllDaySelectionChanged(val allDay: Boolean) : AddEventUserAction()

    data object SelectCalendar : AddEventUserAction()

    data class CalendarSelected(val calendar: Calendar) : AddEventUserAction()

    data object CalendarSelectionDismissed : AddEventUserAction()

    data object ExitAttempt : AddEventUserAction()

    data class UpdateDescription(val newDescription: String) : AddEventUserAction()

    data class UpdateLocation(val newLocation: String) : AddEventUserAction()

    data class UpdateTitle(val newTitle: String) : AddEventUserAction()

    data class UpdateUrls(val newUrls: String) : AddEventUserAction()

    data object SelectEventColor : AddEventUserAction()

    data class SelectedEventColor(val color: ColorsProvider.ColorInfo) : AddEventUserAction()

    data object SelectEventColorDismissed : AddEventUserAction()

    data object SelectTimeZone : AddEventUserAction()

    data object SelectTimeZoneDismissed : AddEventUserAction()

    data class StartDateChanged(val newStartDate: LocalDateTime) : AddEventUserAction()

    data class TimeZoneSelected(val timeZone: TimeZone) : AddEventUserAction()

    data class EndDateChanged(val newEndDate: LocalDateTime) : AddEventUserAction()
}
