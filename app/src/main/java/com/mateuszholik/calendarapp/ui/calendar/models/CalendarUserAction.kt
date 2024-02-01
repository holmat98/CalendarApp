package com.mateuszholik.calendarapp.ui.calendar.models

import com.mateuszholik.calendarapp.ui.base.UserAction
import java.time.LocalDate
import java.time.YearMonth

sealed class CalendarUserAction : UserAction {

    data class SelectedDateChanged(val newDate: LocalDate) : CalendarUserAction()

    data class CurrentMonthChanged(val newMonth: YearMonth) : CalendarUserAction()

    data object RefreshScreen : CalendarUserAction()

    data class EventClicked(val eventId: Long) : CalendarUserAction()

    data object AddEventClicked : CalendarUserAction()

    data object ProfileClicked : CalendarUserAction()
}
