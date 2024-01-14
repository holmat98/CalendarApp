package com.mateuszholik.calendarapp.ui.eventdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiEvent
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiState
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction
import com.mateuszholik.calendarapp.ui.navigation.MainNavigation.EVENT_ID_ARGUMENT
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.CALENDAR_1
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.domain.models.Attendee
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Description
import com.mateuszholik.domain.models.EventDetails
import com.mateuszholik.domain.models.Result
import com.mateuszholik.domain.usecases.GetEventDetailsUseCase
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
class EventDetailsViewModel @Inject constructor(
    private val getEventDetailsUseCase: GetEventDetailsUseCase,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<EventDetailsUiState, EventDetailsUserAction, EventDetailsUiEvent>() {

    private val eventId: Long = savedStateHandle[EVENT_ID_ARGUMENT] ?: 0

    private val _uiState: MutableStateFlow<EventDetailsUiState> =
        MutableStateFlow(EventDetailsUiState.Loading)
    override val uiState: StateFlow<EventDetailsUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<EventDetailsUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<EventDetailsUiEvent>
        get() = _uiEvent.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error on Calendar screen")

        viewModelScope.launch(dispatcherProvider.main()) {
            _uiEvent.emit(EventDetailsUiEvent.Error)
        }
    }

    init {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            getEventDetails()
        }
    }

    override fun performUserAction(action: EventDetailsUserAction) =
        when (action) {
            is EventDetailsUserAction.AttendeeDismissed ->
                handleAttendeeDismissedUserAction()
            is EventDetailsUserAction.AttendeeSelected ->
                handleAttendeeSelectedUserAction(action.attendee)
            is EventDetailsUserAction.DeleteEvent -> Unit
            is EventDetailsUserAction.DeleteEventCancelled -> Unit
            is EventDetailsUserAction.DeleteEventConfirmed -> Unit
            is EventDetailsUserAction.EnterEditMode ->
                handleEnterEditModeUserAction()
            is EventDetailsUserAction.NavigateBack -> Unit
            is EventDetailsUserAction.RetryGetEventDetailsPressed ->
                handleRetryGetEventDetailsPressed()
        }

    private fun handleAttendeeDismissedUserAction() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EventDetailsUiEvent.DismissAttendee)
        }
    }

    private fun handleAttendeeSelectedUserAction(attendee: Attendee) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EventDetailsUiEvent.ShowAttendee(attendee))
        }
    }

    private fun handleRetryGetEventDetailsPressed() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiState.emit(EventDetailsUiState.Loading)

            getEventDetails()
        }
    }

    private fun handleEnterEditModeUserAction() {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiState.update { currentState ->
                if (currentState is EventDetailsUiState.ViewMode) {
                    with(currentState.eventDetails) {
                        EventDetailsUiState.EditMode(
                            title = title,
                            description = description,
                            dateStart = dateStart,
                            dateEnd = dateEnd,
                            allDay = allDay,
                            timezone = "",
                            eventColor = eventColor,
                            location = location,
                            calendar = CALENDAR_1,
                        )
                    }
                } else {
                    currentState
                }
            }
        }
    }

    private suspend fun getEventDetails() {
        val details = getEventDetailsUseCase(eventId)

        if (details is Result.Success) {
            _uiState.emit(EventDetailsUiState.ViewMode(eventDetails = details.data))
        } else {
            _uiState.emit(EventDetailsUiState.NoData)
        }
    }

    sealed class EventDetailsUiState : UiState {

        data object Loading : EventDetailsUiState()

        data object NoData : EventDetailsUiState()

        data class ViewMode(val eventDetails: EventDetails) : EventDetailsUiState()

        data class EditMode(
            val title: String,
            val description: Description,
            val dateStart: LocalDateTime,
            val dateEnd: LocalDateTime,
            val allDay: Boolean,
            val timezone: String,
            val eventColor: Int?,
            val location: String,
            val calendar: Calendar,
        ) : EventDetailsUiState()
    }

    sealed class EventDetailsUiEvent : UiEvent {

        data object Error : EventDetailsUiEvent()

        data class ShowAttendee(val attendee: Attendee) : EventDetailsUiEvent()

        data object DismissAttendee : EventDetailsUiEvent()
    }

    sealed class EventDetailsUserAction : UserAction {

        data object EnterEditMode : EventDetailsUserAction()

        data object NavigateBack : EventDetailsUserAction()

        data object DeleteEvent : EventDetailsUserAction()

        data object DeleteEventConfirmed : EventDetailsUserAction()

        data object DeleteEventCancelled : EventDetailsUserAction()

        data class AttendeeSelected(val attendee: Attendee) : EventDetailsUserAction()

        data object AttendeeDismissed : EventDetailsUserAction()

        data object RetryGetEventDetailsPressed : EventDetailsUserAction()
    }
}
