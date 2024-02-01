package com.mateuszholik.calendarapp.ui.eventdetails.models

import androidx.compose.runtime.Stable
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.domain.models.EventDetails

@Stable
sealed class EventDetailsUiState : UiState {

    data object Loading : EventDetailsUiState()

    data object NoData : EventDetailsUiState()

    data class ViewMode(val eventDetails: EventDetails) : EventDetailsUiState()
}
