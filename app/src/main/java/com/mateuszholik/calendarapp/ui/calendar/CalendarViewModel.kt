package com.mateuszholik.calendarapp.ui.calendar

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.calendar.models.CalendarUiEvent
import com.mateuszholik.calendarapp.ui.calendar.models.CalendarUiState
import com.mateuszholik.calendarapp.ui.calendar.models.CalendarUserAction
import com.mateuszholik.dateutils.extensions.toYearMonth
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
        MutableStateFlow(getInitialLoadingState())
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

    override fun performUserAction(action: CalendarUserAction) {
        when (action) {
            is CalendarUserAction.SelectedDateChanged ->
                handleSelectedDateChangedAction(action.newDate)
            is CalendarUserAction.CurrentMonthChanged ->
                handleCurrentMonthChangedAction(action.newMonth)
            is CalendarUserAction.RefreshScreen ->
                handleRefreshScreenAction()
            is CalendarUserAction.EventClicked ->
                handleEventClickedAction(action.eventId)
            is CalendarUserAction.AddEventClicked ->
                handleAddEventClickedAction()
            is CalendarUserAction.ProfileClicked ->
                handleCalendarProfilesClicked()
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

    private fun handleRefreshScreenAction() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiState.update {
                val (currentMonth, currentDate) = if (it is CalendarUiState.Loading) {
                    it.currentMonth to it.currentDate
                } else {
                    val calendarInfo = it as CalendarUiState.CalendarInfo

                    calendarInfo.currentMonth to calendarInfo.currentDate
                }

                getUpdatedUiState(currentMonth, currentDate)
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

    private fun handleCalendarProfilesClicked() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(CalendarUiEvent.NavigateToCalendarsSelection)
        }
    }

    private fun getInitialLoadingState(): CalendarUiState {
        val currentDate = currentDateProvider.provide()
        val currentMonth = currentDate.toYearMonth()

        return CalendarUiState.Loading(
            currentDate = currentDate,
            currentMonth = currentMonth,
        )
    }

    private suspend fun getUpdatedUiState(
        currentMonth: YearMonth,
        currentDate: LocalDate,
    ): CalendarUiState {
        val daysWithEvents = getDaysWithEventsForMonthUseCase(currentMonth)
        val events = getEventsForDayUseCase(currentDate)

        return CalendarUiState.CalendarInfo(
            currentDate = currentDate,
            currentMonth = currentMonth,
            events = events,
            daysWithEvents = daysWithEvents
        )
    }
}
