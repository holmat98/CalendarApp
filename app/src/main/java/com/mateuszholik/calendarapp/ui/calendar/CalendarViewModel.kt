package com.mateuszholik.calendarapp.ui.calendar

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.extensions.toYearMonth
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.calendarapp.provider.DispatcherProvider
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction
import com.mateuszholik.domain.models.Event
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCase
import com.mateuszholik.domain.usecases.GetEventsForDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsForDayUseCase: GetEventsForDayUseCase,
    private val getDaysWithEventsForMonthUseCase: GetDaysWithEventsForMonthUseCase,
    private val dispatcherProvider: DispatcherProvider,
    currentDateProvider: CurrentDateProvider,
) : BaseViewModel<CalendarUiState, CalendarUserAction, CalendarUiEvent>() {

    private val _uiState: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(CalendarUiState.Loading)
    override val uiState: StateFlow<CalendarUiState>
        get() = _uiState

    private val _uiEvent: MutableSharedFlow<CalendarUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<CalendarUiEvent>
        get() = _uiEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error on Calendar screen")
        _uiEvent.tryEmit(CalendarUiEvent.Error)
    }

    init {
        handleSelectedDateChangedAction(currentDateProvider.provide())
    }

    override fun performUserAction(action: CalendarUserAction) {
        when (action) {
            is CalendarUserAction.SelectedDateChanged ->
                handleSelectedDateChangedAction(action.newDate)
            is CalendarUserAction.EventClicked ->
                handleEventClickedAction(action.eventId)
        }
    }

    private fun handleSelectedDateChangedAction(newDate: LocalDate) {
        viewModelScope.launch(dispatcherProvider.io() + exceptionHandler) {
            val state = _uiState.value

            _uiState.update { currentState ->
                if (currentState is CalendarUiState.CalendarInfo) {
                    currentState.copy(loading = true)
                } else {
                    currentState
                }
            }

            val eventsForDay = getEventsForDayUseCase(newDate)
            val daysWithEvents = if (state is CalendarUiState.CalendarInfo
                && state.currentDate.toYearMonth() == newDate.toYearMonth()
            ) {
                state.daysWithEvents
            } else {
                getDaysWithEventsForMonthUseCase(newDate.toYearMonth())
            }

            _uiState.emit(
                CalendarUiState.CalendarInfo(
                    currentDate = newDate,
                    events = eventsForDay,
                    daysWithEvents = daysWithEvents
                )
            )
        }
    }

    private fun handleEventClickedAction(eventId: Long) {
        viewModelScope.launch(exceptionHandler) {
            _uiEvent.emit(CalendarUiEvent.NavigateToEvent(eventId))
        }
    }

    sealed class CalendarUiState : UiState {

        data object Loading : CalendarUiState()

        data class CalendarInfo(
            val currentDate: LocalDate,
            val events: List<Event>,
            val daysWithEvents: List<LocalDate>,
            val loading: Boolean = false,
        ) : CalendarUiState()
    }

    sealed class CalendarUiEvent : UiEvent {

        data class NavigateToEvent(val eventId: Long) : CalendarUiEvent()

        data object Error : CalendarUiEvent()
    }

    sealed class CalendarUserAction : UserAction {

        data class SelectedDateChanged(val newDate: LocalDate) : CalendarUserAction()

        data class EventClicked(val eventId: Long) : CalendarUserAction()
    }
}
