package com.mateuszholik.calendarapp.ui.editevent

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUiEvent
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUiState
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction
import com.mateuszholik.calendarapp.ui.navigation.MainNavigation.EVENT_ID_ARGUMENT
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Description
import com.mateuszholik.domain.models.EditableEventDetails
import com.mateuszholik.domain.models.Result
import com.mateuszholik.domain.usecases.GetCalendarsUseCase
import com.mateuszholik.domain.usecases.GetEditableEventDetailsUseCase
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
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val getEditableEventDetailsUseCase: GetEditableEventDetailsUseCase,
    private val getCalendarsUseCase: GetCalendarsUseCase,
    private val colorsProvider: ColorsProvider,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EditEventUiState, EditEventUserAction, EditEventUiEvent>() {

    private val eventId: Long = savedStateHandle[EVENT_ID_ARGUMENT] ?: 0

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error on Calendar screen")

        viewModelScope.launch(dispatcherProvider.main()) {
            _uiEvent.emit(EditEventUiEvent.Error)
        }
    }

    private val _uiState: MutableStateFlow<EditEventUiState> =
        MutableStateFlow(EditEventUiState.Loading)
    override val uiState: StateFlow<EditEventUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<EditEventUiEvent>()
    override val uiEvent: SharedFlow<EditEventUiEvent>
        get() = _uiEvent.asSharedFlow()

    init {
        getEventDetails()
    }

    override fun performUserAction(action: EditEventUserAction) =
        when (action) {
            is EditEventUserAction.CalendarSelected ->
                handleCalendarSelected(action.calendar)
            is EditEventUserAction.CalendarSelectionDismissed ->
                handleCalendarSelectionDismissed()
            is EditEventUserAction.SelectCalendar ->
                handleSelectCalendar()
            is EditEventUserAction.ExitAttempt ->
                handleExitAttempt()
            is EditEventUserAction.ExitAttemptCancelled ->
                handleExitAttemptCancelled()
            is EditEventUserAction.ExitAttemptConfirmed ->
                handleExitAttemptConfirmed()
            is EditEventUserAction.UpdateDescription ->
                handleUpdateDescription(action.newDescription)
            is EditEventUserAction.UpdateLocation ->
                handleUpdateLocation(action.newLocation)
            is EditEventUserAction.UpdateTitle ->
                handleUpdateTitle(action.newTitle)
            is EditEventUserAction.SelectEventColor ->
                handleSelectEventColor()
            is EditEventUserAction.SelectEventColorDismissed ->
                handleSelectEventColorDismissed()
            is EditEventUserAction.SelectedEventColor ->
                handleSelectedEventColor(action.color)
            is EditEventUserAction.AllDaySelectionChanged ->
                handleAllDaySelectionChanged(action.allDay)
            is EditEventUserAction.EndDateChanged ->
                handleEndDateChanged(action.newEndDate)
            is EditEventUserAction.StartDateChanged ->
                handleStartDateChanged(action.newStartDate)
        }

    private fun getEventDetails() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val eventDetailsResult = getEditableEventDetailsUseCase(eventId)

            if (eventDetailsResult is Result.Success) {
                _uiState.emit(eventDetailsResult.data.toEventDetails())
            } else {
                Timber.d("Testowanie: error")
            }
        }
    }

    private fun handleCalendarSelected(calendar: Calendar) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.DismissCalendarSelection)
            _uiState.updateOnEventDetails { it.copy(calendar = calendar) }
        }
    }

    private fun handleCalendarSelectionDismissed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.DismissCalendarSelection)
        }
    }

    private fun handleSelectCalendar() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val calendars = getCalendarsUseCase()
            _uiEvent.emit(EditEventUiEvent.ShowCalendarSelection(calendars))
        }
    }

    private fun handleExitAttempt() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.ShowExitDialog)
        }
    }

    private fun handleExitAttemptCancelled() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.DismissExitDialog)
        }
    }

    private fun handleExitAttemptConfirmed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.DismissExitDialog)
            _uiEvent.emit(EditEventUiEvent.NavigateBack)
        }
    }

    private fun handleUpdateDescription(newDescription: String) {
        _uiState.updateOnEventDetails { it.copy(description = it.description.copyWith(newDescription)) }
    }

    private fun handleUpdateLocation(newLocation: String) {
        _uiState.updateOnEventDetails { it.copy(location = newLocation) }
    }

    private fun handleUpdateTitle(newTitle: String) {
        _uiState.updateOnEventDetails { it.copy(title = newTitle) }
    }

    private fun handleSelectEventColor() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val colors = colorsProvider.provide()

            _uiEvent.emit(EditEventUiEvent.ShowEventColorSelection(colors))
        }
    }

    private fun handleSelectEventColorDismissed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.DismissColorEventSelection)
        }
    }

    private fun handleSelectedEventColor(color: ColorsProvider.ColorInfo) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EditEventUiEvent.DismissColorEventSelection)
            _uiState.updateOnEventDetails { it.copy(eventColor = color) }
        }
    }

    private fun handleAllDaySelectionChanged(allDay: Boolean) {
        _uiState.updateOnEventDetails { it.copy(allDay = allDay) }
    }

    private fun handleStartDateChanged(newStartDate: LocalDateTime) {
        _uiState.updateOnEventDetails { it.copy(dateStart = newStartDate) }
    }

    private fun handleEndDateChanged(newEndDate: LocalDateTime) {
        _uiState.updateOnEventDetails { it.copy(dateEnd = newEndDate) }
    }

    private fun MutableStateFlow<EditEventUiState>.updateOnEventDetails(transform: (EditEventUiState.EventDetails) -> EditEventUiState) =
        update { currentState ->
            if (currentState is EditEventUiState.EventDetails) {
                transform(currentState)
            } else {
                currentState
            }
        }

    private fun EditableEventDetails.toEventDetails(): EditEventUiState.EventDetails =
        EditEventUiState.EventDetails(
            id = id,
            title = title,
            description = description,
            dateStart = dateStart,
            dateEnd = dateEnd,
            allDay = allDay,
            eventColor = eventColor?.let {
                Timber.d("Testowanie: color = $it")
                ColorsProvider.ColorInfo(it, R.string.color_default)
            },
            location = location,
            calendar = calendar
        )

    sealed class EditEventUiState : UiState {

        data object Loading : EditEventUiState()

        data class EventDetails(
            val id: Long,
            val title: String,
            val description: Description,
            val dateStart: LocalDateTime,
            val dateEnd: LocalDateTime,
            val allDay: Boolean,
            val eventColor: ColorsProvider.ColorInfo?,
            val location: String,
            val calendar: Calendar?,
        ) : EditEventUiState()
    }

    sealed class EditEventUiEvent : UiEvent {

        data object Error : EditEventUiEvent()

        data object DismissCalendarSelection : EditEventUiEvent()

        data class ShowCalendarSelection(val calendars: List<Calendar>) : EditEventUiEvent()

        data object ShowExitDialog : EditEventUiEvent()

        data object DismissExitDialog : EditEventUiEvent()

        data object NavigateBack : EditEventUiEvent()

        data object DismissColorEventSelection : EditEventUiEvent()

        data class ShowEventColorSelection(val colors: List<ColorsProvider.ColorInfo>) :
            EditEventUiEvent()
    }

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
    }
}
