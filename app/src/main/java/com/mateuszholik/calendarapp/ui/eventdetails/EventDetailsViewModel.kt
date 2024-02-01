package com.mateuszholik.calendarapp.ui.eventdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.eventdetails.models.EventDetailsUiEvent
import com.mateuszholik.calendarapp.ui.eventdetails.models.EventDetailsUiState
import com.mateuszholik.calendarapp.ui.eventdetails.models.EventDetailsUserAction
import com.mateuszholik.calendarapp.ui.navigation.MainNavigation.EVENT_ID_ARGUMENT
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.domain.models.Attendee
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
import kotlinx.coroutines.launch
import timber.log.Timber
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
            is EventDetailsUserAction.EditEventPressed ->
                handleEnterEditEventPressedUserAction(action.eventId)
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

    private fun handleEnterEditEventPressedUserAction(eventId: Long) {
        viewModelScope.launch(dispatcherProvider.main() + exceptionHandler) {
            _uiEvent.emit(EventDetailsUiEvent.GoToEventDetails(eventId))
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
}
