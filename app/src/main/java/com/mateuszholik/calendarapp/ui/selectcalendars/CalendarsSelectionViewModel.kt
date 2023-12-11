package com.mateuszholik.calendarapp.ui.selectcalendars

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.selectcalendars.CalendarsSelectionViewModel.CalendarProfilesUserAction
import com.mateuszholik.calendarapp.ui.selectcalendars.CalendarsSelectionViewModel.CalendarProfilesUiState
import com.mateuszholik.calendarapp.ui.selectcalendars.CalendarsSelectionViewModel.CalendarProfilesUiEvent
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.usecases.GetCalendarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarsSelectionViewModel @Inject constructor(
    private val getCalendarsUseCase: GetCalendarsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel<CalendarProfilesUiState, CalendarProfilesUserAction, CalendarProfilesUiEvent>() {

    private val _uiState: MutableStateFlow<CalendarProfilesUiState> =
        MutableStateFlow(CalendarProfilesUiState.Loading)
    override val uiState: StateFlow<CalendarProfilesUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<CalendarProfilesUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<CalendarProfilesUiEvent>
        get() = _uiEvent.asSharedFlow()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error in CalendarProfilesViewModel")

        viewModelScope.launch {
            _uiEvent.emit(CalendarProfilesUiEvent.Error)
        }
    }

    init {
        getCalendars()
    }

    override fun performUserAction(action: CalendarProfilesUserAction) {
        when (action) {
            is CalendarProfilesUserAction.OnCalendarSelectionChanged ->
                handleOnCalendarSelectionChanged(action.calendarId, action.isVisible)
            is CalendarProfilesUserAction.OnCalendarsConfirmed ->
                handleOnCalendarsConfirmed()
        }
    }

    private fun getCalendars() {
        viewModelScope.launch(dispatcherProvider.main() + handler) {
            val calendars = getCalendarsUseCase()

            _uiState.emit(CalendarProfilesUiState.Calendars(calendars))
        }
    }

    private fun handleOnCalendarSelectionChanged(calendarId: Long, isVisible: Boolean) {

    }

    private fun handleOnCalendarsConfirmed() {
        viewModelScope.launch(dispatcherProvider.main() + handler) {
            _uiEvent.emit(CalendarProfilesUiEvent.CalendarsSelected)
        }
    }

    sealed class CalendarProfilesUiState : UiState {

        data object Loading : CalendarProfilesUiState()

        data class Calendars(val calendars: Map<String, List<Calendar>>) : CalendarProfilesUiState()
    }

    sealed class CalendarProfilesUiEvent : UiEvent {

        data object Error : CalendarProfilesUiEvent()

        data object CalendarsSelected : CalendarProfilesUiEvent()
    }

    sealed class CalendarProfilesUserAction : UserAction {

        data class OnCalendarSelectionChanged(
            val calendarId: Long,
            val isVisible: Boolean,
        ) : CalendarProfilesUserAction()

        data object OnCalendarsConfirmed : CalendarProfilesUserAction()
    }
}
