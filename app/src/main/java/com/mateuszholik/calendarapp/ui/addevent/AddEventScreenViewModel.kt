package com.mateuszholik.calendarapp.ui.addevent

import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiEvent
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiState
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUserAction
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.dateutils.extensions.copy
import com.mateuszholik.domain.models.Calendar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddEventScreenViewModel @Inject constructor(
    private val currentDateProvider: CurrentDateProvider,
    private val colorsProvider: ColorsProvider,
    private val dispatcherProvider: DispatcherProvider,
) : BaseViewModel<AddEventUiState, AddEventUserAction, AddEventUiEvent>() {

    private val _uiState = MutableStateFlow(getInitialUiState())
    override val uiState: StateFlow<AddEventUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<AddEventUiEvent>()
    override val uiEvent: SharedFlow<AddEventUiEvent>
        get() = _uiEvent.asSharedFlow()

    override fun performUserAction(action: AddEventUserAction) {
        if (action is AddEventUserAction.AllDaySelectionChanged) {
            _uiState.update { it.copy(allDay = action.allDay) }
        }
    }

    private fun getInitialUiState(): AddEventUiState {
        val startDateTime = currentDateProvider
            .provideDateTime()
            .plusHours(1)
            .copy(minute = 0, second = 0)

        return AddEventUiState(
            title = "",
            description = "",
            allDay = false,
            startDate = startDateTime,
            endDate = startDateTime.plusHours(1),
            timezone = "",
            urls = "",
            calendar = Calendar(
                id = 1,
                accountName = "test",
                calendarName = "test",
                isVisible = true,
                color = null
            ),
            color = colorsProvider.provideDefault(),
            location = "",
        )
    }
}
