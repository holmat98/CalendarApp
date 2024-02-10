package com.mateuszholik.calendarapp.ui.addevent.models

import com.mateuszholik.calendarapp.ui.base.UserAction

sealed class AddEventUserAction : UserAction {

    data class AllDaySelectionChanged(val allDay: Boolean) : AddEventUserAction()
}
