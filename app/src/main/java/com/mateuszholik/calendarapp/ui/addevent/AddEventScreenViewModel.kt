package com.mateuszholik.calendarapp.ui.addevent

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiEvent
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiState
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUserAction
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.calendarapp.ui.provider.MinutesProvider
import com.mateuszholik.calendarapp.ui.provider.TimezoneProvider
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.dateutils.Minutes
import com.mateuszholik.dateutils.extensions.copy
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Description
import com.mateuszholik.domain.models.NewEvent
import com.mateuszholik.domain.usecases.CreateEventUseCase
import com.mateuszholik.domain.usecases.GetCalendarUseCase
import com.mateuszholik.domain.usecases.GetCalendarsUseCase
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
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class AddEventScreenViewModel @Inject constructor(
    private val currentDateProvider: CurrentDateProvider,
    private val colorsProvider: ColorsProvider,
    private val getCalendarsUseCase: GetCalendarsUseCase,
    private val getCalendarUseCase: GetCalendarUseCase,
    private val timezoneProvider: TimezoneProvider,
    private val minutesProvider: MinutesProvider,
    private val createEventUseCase: CreateEventUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel<AddEventUiState, AddEventUserAction, AddEventUiEvent>() {

    private val _uiState: MutableStateFlow<AddEventUiState> =
        MutableStateFlow(AddEventUiState.Loading)
    override val uiState: StateFlow<AddEventUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AddEventUiEvent>()
    override val uiEvent: SharedFlow<AddEventUiEvent>
        get() = _uiEvent.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error on add event screen")

        viewModelScope.launch(dispatcherProvider.main()) {
            _uiEvent.emit(AddEventUiEvent.Error)
        }
    }

    init {
        setInitialAddEventData()
    }

    override fun performUserAction(action: AddEventUserAction) =
        when (action) {
            is AddEventUserAction.AllDaySelectionChanged ->
                handleAllDaySelectionChanged(action.allDay)
            is AddEventUserAction.CalendarSelected ->
                handleCalendarSelected(action.calendar)
            is AddEventUserAction.CalendarSelectionDismissed ->
                handleCalendarSelectionDismissed()
            is AddEventUserAction.EndDateChanged ->
                handleEndDateChanged(action.newEndDate)
            is AddEventUserAction.ExitAttempt ->
                handleExitAttempt()
            is AddEventUserAction.SelectCalendar ->
                handleSelectCalendar()
            is AddEventUserAction.SelectEventColor ->
                handleSelectEventColor()
            is AddEventUserAction.SelectEventColorDismissed ->
                handleSelectEventColorDismissed()
            is AddEventUserAction.SelectReminder ->
                handleSelectReminder()
            is AddEventUserAction.SelectReminderDismissed ->
                handleSelectReminderDismissed()
            is AddEventUserAction.SelectedEventColor ->
                handleSelectedEventColor(action.color)
            is AddEventUserAction.StartDateChanged ->
                handleStartDateChanged(action.newStartDate)
            is AddEventUserAction.UpdateDescription ->
                handleUpdateDescription(action.newDescription)
            is AddEventUserAction.SaveEvent ->
                handleSaveEvent()
            is AddEventUserAction.SelectTimeZone ->
                handleSelectTimeZone()
            is AddEventUserAction.SelectTimeZoneDismissed ->
                handleSelectTimeZoneDismissed()
            is AddEventUserAction.ReminderSelected ->
                handleReminderSelected(action.minutes)
            is AddEventUserAction.UpdateLocation ->
                handleUpdateLocation(action.newLocation)
            is AddEventUserAction.UpdateTitle ->
                handleUpdateTitle(action.newTitle)
            is AddEventUserAction.UpdateUrls ->
                handleUpdateUrls(action.newUrls)
            is AddEventUserAction.TimeZoneSelected ->
                handleTimeZoneSelected(action.timeZone)
        }

    private fun handleAllDaySelectionChanged(allDay: Boolean) {
        _uiState.updateAddEventData {
            it.copy(
                allDay = allDay,
                timezone = if (allDay) {
                    timezoneProvider.provideUTC()
                } else {
                    it.timezone
                }
            )
        }
    }

    private fun handleCalendarSelected(calendar: Calendar) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissCalendarSelection)
            _uiState.updateAddEventData { it.copy(calendar = calendar) }
        }
    }

    private fun handleCalendarSelectionDismissed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissCalendarSelection)
        }
    }

    private fun handleEndDateChanged(newEndDate: LocalDateTime) {
        _uiState.updateAddEventData { it.copy(endDate = newEndDate) }
    }

    private fun handleExitAttempt() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.NavigateBack)
        }
    }

    private fun handleSelectCalendar() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val calendars = getCalendarsUseCase()
            _uiEvent.emit(AddEventUiEvent.ShowCalendarSelection(calendars))
        }
    }

    private fun handleSelectEventColor() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val colors = colorsProvider.provide()

            _uiEvent.emit(AddEventUiEvent.ShowEventColorSelection(colors))
        }
    }

    private fun handleSelectEventColorDismissed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissColorEventSelection)
        }
    }

    private fun handleSelectReminder() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.ShowReminderSelection(minutesProvider.provide()))
        }
    }

    private fun handleSelectReminderDismissed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissReminderSelection)
        }
    }

    private fun handleSelectedEventColor(color: ColorsProvider.ColorInfo) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissColorEventSelection)
            _uiState.updateAddEventData { it.copy(color = color) }
        }
    }

    private fun handleStartDateChanged(newStartDate: LocalDateTime) {
        _uiState.updateAddEventData { it.copy(startDate = newStartDate) }
    }

    private fun handleUpdateDescription(newDescription: String) {
        _uiState.updateAddEventData { it.copy(description = newDescription) }
    }

    private fun handleSaveEvent() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val currentState = _uiState.value
            if (currentState is AddEventUiState.AddEventData) {
                val newEvent = currentState.toNewEvent()

                createEventUseCase(newEvent)

                _uiEvent.emit(AddEventUiEvent.NavigateBack)
            }
        }
    }

    private fun handleSelectTimeZone() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val timezones = timezoneProvider.provideAllTimezones()

            _uiEvent.emit(AddEventUiEvent.ShowTimeZoneSelection(timezones))
        }
    }

    private fun handleSelectTimeZoneDismissed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissTimeZoneSelection)
        }
    }

    private fun handleReminderSelected(minutes: Minutes) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissReminderSelection)
            _uiState.updateAddEventData { it.copy(reminder = minutes) }
        }
    }

    private fun handleUpdateLocation(newLocation: String) {
        _uiState.updateAddEventData { it.copy(location = newLocation) }
    }

    private fun handleUpdateTitle(newTitle: String) {
        _uiState.updateAddEventData { it.copy(title = newTitle) }
    }

    private fun handleUpdateUrls(newUrls: String) {
        _uiState.updateAddEventData { it.copy(urls = newUrls) }
    }

    private fun handleTimeZoneSelected(timeZone: TimeZone) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(AddEventUiEvent.DismissTimeZoneSelection)
            _uiState.updateAddEventData { it.copy(timezone = timeZone) }
        }
    }

    private fun setInitialAddEventData() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            val startDateTime = currentDateProvider
                .provideDateTime()
                .plusHours(1)
                .copy(minute = 0, second = 0)

            _uiState.emit(
                AddEventUiState.AddEventData(
                    title = "",
                    description = "",
                    allDay = false,
                    startDate = startDateTime,
                    endDate = startDateTime.plusHours(1),
                    timezone = timezoneProvider.provideDefault(),
                    urls = "",
                    calendar = getCalendarUseCase(),
                    color = colorsProvider.provideDefault(),
                    location = "",
                    reminder = null,
                )
            )
        }
    }

    private fun MutableStateFlow<AddEventUiState>.updateAddEventData(
        transform: (AddEventUiState.AddEventData) -> AddEventUiState,
    ): Unit =
        update { currentState ->
            if (currentState is AddEventUiState.AddEventData) {
                transform(currentState)
            } else {
                currentState
            }
        }

    private fun AddEventUiState.AddEventData.toNewEvent(): NewEvent =
        NewEvent(
            title = title,
            description = Description.from(description, urls.split(";")),
            allDay = allDay,
            startDate = startDate,
            endDate = endDate,
            timezone = timezone.id,
            calendarId = calendar.id,
            eventColor = color.value,
            location = location,
            reminder = reminder?.value,
        )
}
