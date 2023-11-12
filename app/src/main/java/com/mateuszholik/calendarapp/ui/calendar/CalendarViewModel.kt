package com.mateuszholik.calendarapp.ui.calendar

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.extensions.toYearMonth
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction
import com.mateuszholik.domain.models.Event
import com.mateuszholik.domain.models.Result
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCase
import com.mateuszholik.domain.usecases.GetEventsForDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsForDayUseCase: GetEventsForDayUseCase,
    private val getDaysWithEventsForMonthUseCase: GetDaysWithEventsForMonthUseCase,
) : BaseViewModel<CalendarUiState, CalendarUserAction, CalendarUiEvent>() {

    private val _uiState: MutableStateFlow<CalendarUiState> =
        MutableStateFlow(CalendarUiState.Loading)
    override val uiState: StateFlow<CalendarUiState>
        get() = _uiState

    private val _uiEvent: MutableSharedFlow<CalendarUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<CalendarUiEvent>
        get() = _uiEvent

    override fun performUserAction(action: CalendarUserAction) {
        when (action) {
            is CalendarUserAction.SelectedDateChanged -> {
                viewModelScope.launch(Dispatchers.IO) {

                    val eventsForDay = getEventsForDayUseCase(action.newDate)
                    val daysWithEvents = getDaysWithEventsForMonthUseCase(action.newDate.toYearMonth())

                    _uiState.emit(
                        CalendarUiState.CalendarInfo(
                            currentDate = action.newDate,
                            events = (eventsForDay as Result.Success).data,
                            daysWithEvents = (daysWithEvents as Result.Success).data
                        )
                    )
                }
            }
            is CalendarUserAction.EventClicked -> {
                viewModelScope.launch {
                    _uiEvent.emit(CalendarUiEvent.NavigateToEvent(action.eventId))
                }
            }
        }
    }

    sealed class CalendarUiState : UiState {

        data object Loading : CalendarUiState()

        data class CalendarInfo(
            val currentDate: LocalDate,
            val events: List<Event>,
            val daysWithEvents: List<LocalDate>,
        ) : CalendarUiState()
    }

    sealed class CalendarUiEvent : UiEvent {

        data class NavigateToEvent(val eventId: Long) : CalendarUiEvent()
    }

    sealed class CalendarUserAction : UserAction {

        data class SelectedDateChanged(val newDate: LocalDate) : CalendarUserAction()

        data class EventClicked(val eventId: Long) : CalendarUserAction()
    }
}
