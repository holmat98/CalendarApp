package com.mateuszholik.calendarapp.ui.calendar

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.extensions.toYearMonth
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.common.provider.DispatcherProvider
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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsForDayUseCase: GetEventsForDayUseCase,
    private val getDaysWithEventsForMonthUseCase: GetDaysWithEventsForMonthUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val currentDateProvider: CurrentDateProvider,
) : BaseViewModel<CalendarUiState, CalendarUserAction, CalendarUiEvent>() {

    private val _uiState: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(CalendarUiState.Loading)
    override val uiState: StateFlow<CalendarUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<CalendarUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<CalendarUiEvent>
        get() = _uiEvent.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error on Calendar screen")
        viewModelScope.launch(dispatcherProvider.main()) {
            _uiEvent.emit(CalendarUiEvent.Error)
        }
    }

    init {
        setInitialUiState()
    }

    override fun performUserAction(action: CalendarUserAction) {
        when (action) {
            is CalendarUserAction.SelectedDateChanged ->
                handleSelectedDateChangedAction(action.newDate)
            is CalendarUserAction.CurrentMonthChanged ->
                handleCurrentMonthChangedAction(action.newMonth)
            is CalendarUserAction.EventClicked ->
                handleEventClickedAction(action.eventId)
            is CalendarUserAction.AddEventClicked ->
                handleAddEventClickedAction()
        }
    }

    private fun setInitialUiState() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val currentDate = currentDateProvider.provide()
            val currentMonth = currentDate.toYearMonth()

            val daysWithEvents = getDaysWithEventsForMonthUseCase(currentMonth)
            val events = getEventsForDayUseCase(currentDate)

            _uiState.emit(
                CalendarUiState.CalendarInfo(
                    currentDate = currentDate,
                    currentMonth = currentMonth,
                    events = events,
                    daysWithEvents = daysWithEvents
                )
            )
        }
    }

    private fun handleSelectedDateChangedAction(newDate: LocalDate) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiState.update { currentState ->
                if (currentState is CalendarUiState.CalendarInfo) {
                    currentState.copy(
                        currentDate = newDate,
                        events = getEventsForDayUseCase(newDate)
                    )
                } else {
                    currentState
                }
            }
        }
    }

    private fun handleCurrentMonthChangedAction(yearMonth: YearMonth) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiState.update { currentState ->
                if (currentState is CalendarUiState.CalendarInfo) {
                    currentState.copy(
                        currentMonth = yearMonth,
                        daysWithEvents = getDaysWithEventsForMonthUseCase(yearMonth)
                    )
                } else {
                    currentState
                }
            }
        }
    }

    private fun handleEventClickedAction(eventId: Long) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(CalendarUiEvent.NavigateToEvent(eventId))
        }
    }

    private fun handleAddEventClickedAction() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(CalendarUiEvent.NavigateToAddEvent)
        }
    }

    sealed class CalendarUiState : UiState {

        data object Loading : CalendarUiState()

        data class CalendarInfo(
            val currentDate: LocalDate,
            val currentMonth: YearMonth,
            val events: List<Event>,
            val daysWithEvents: List<LocalDate>,
        ) : CalendarUiState()
    }

    sealed class CalendarUiEvent : UiEvent {

        data class NavigateToEvent(val eventId: Long) : CalendarUiEvent()

        data object NavigateToAddEvent : CalendarUiEvent()

        data object Error : CalendarUiEvent()
    }

    sealed class CalendarUserAction : UserAction {

        data class SelectedDateChanged(val newDate: LocalDate) : CalendarUserAction()

        data class CurrentMonthChanged(val newMonth: YearMonth) : CalendarUserAction()

        data class EventClicked(val eventId: Long) : CalendarUserAction()

        data object AddEventClicked : CalendarUserAction()
    }
}
