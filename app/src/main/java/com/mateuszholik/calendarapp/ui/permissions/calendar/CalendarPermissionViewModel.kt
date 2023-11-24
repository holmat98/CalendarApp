package com.mateuszholik.calendarapp.ui.permissions.calendar

import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUiState
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUiEvent
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUserAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarPermissionViewModel @Inject constructor() :
    BaseViewModel<CalendarPermissionUiState, CalendarPermissionUserAction, CalendarPermissionUiEvent>() {

    override val uiState: StateFlow<CalendarPermissionUiState>
        get() = TODO("Not yet implemented")

    override val uiEvent: SharedFlow<CalendarPermissionUiEvent>
        get() = TODO("Not yet implemented")

    override fun performUserAction(action: CalendarPermissionUserAction) {
        TODO("Not yet implemented")
    }

    sealed class CalendarPermissionUiState : UiState

    sealed class CalendarPermissionUiEvent : UiEvent

    sealed class CalendarPermissionUserAction : UserAction
}
