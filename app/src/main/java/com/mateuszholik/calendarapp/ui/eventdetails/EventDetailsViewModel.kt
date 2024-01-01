package com.mateuszholik.calendarapp.ui.eventdetails

import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiEvent
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUiState
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsViewModel.EventDetailsUserAction
import com.mateuszholik.domain.models.EventDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor() :
    BaseViewModel<EventDetailsUiState, EventDetailsUserAction, EventDetailsUiEvent>() {

    private val _uiState: MutableStateFlow<EventDetailsUiState> =
        MutableStateFlow(EventDetailsUiState.Loading)
    override val uiState: StateFlow<EventDetailsUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<EventDetailsUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<EventDetailsUiEvent>
        get() = _uiEvent.asSharedFlow()

    override fun performUserAction(action: EventDetailsUserAction) {
    }

    sealed class EventDetailsUiState : UiState {

        data object Loading : EventDetailsUiState()

        data object NoData : EventDetailsUiState()

        data class ViewMode(val eventDetails: EventDetails) : EventDetailsUiState()

        data class EditMode(val id: Long) : EventDetailsUiState()
    }

    sealed class EventDetailsUiEvent : UiEvent {

    }

    sealed class EventDetailsUserAction : UserAction {

        data object EnterEditMode : EventDetailsUserAction()

        data object NavigateBack : EventDetailsUserAction()

        data object DeleteEvent : EventDetailsUserAction()

        data object DeleteEventConfirmed : EventDetailsUserAction()

        data object DeleteEventCancelled : EventDetailsUserAction()
    }
}
