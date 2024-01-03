package com.mateuszholik.calendarapp.ui.eventdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiEvent
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiState
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction
import com.mateuszholik.calendarapp.ui.navigation.MainNavigation.EVENT_ID_ARGUMENT
import com.mateuszholik.common.provider.DispatcherProvider
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
            val details = getEventDetailsUseCase(eventId)

            if (details is Result.Success) {
                _uiState.emit(EventDetailsUiState.ViewMode(eventDetails = details.data))
            } else {
                Timber.d("Testowanie: There is no event")
            }
        }
    }

    override fun performUserAction(action: EventDetailsUserAction) {
    }

    sealed class EventDetailsUiState : UiState {

        data object Loading : EventDetailsUiState()

        data object NoData : EventDetailsUiState()

        data class ViewMode(val eventDetails: EventDetails) : EventDetailsUiState()

        data class EditMode(val id: Long) : EventDetailsUiState()
    }

    sealed class EventDetailsUiEvent : UiEvent {

        data object Error : EventDetailsUiEvent()
    }

    sealed class EventDetailsUserAction : UserAction {

        data object EnterEditMode : EventDetailsUserAction()

        data object NavigateBack : EventDetailsUserAction()

        data object DeleteEvent : EventDetailsUserAction()

        data object DeleteEventConfirmed : EventDetailsUserAction()

        data object DeleteEventCancelled : EventDetailsUserAction()
    }
}
