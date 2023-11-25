package com.mateuszholik.calendarapp.ui.permissions.calendar

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.permissions.PermissionManager
import com.mateuszholik.calendarapp.permissions.ReadCalendarPermissionManager
import com.mateuszholik.calendarapp.permissions.WriteCalendarPermissionManager
import com.mateuszholik.calendarapp.provider.DispatcherProvider
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.base.UserAction
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUiEvent
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUiState
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUserAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarPermissionViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val readCalendarPermissionManager: ReadCalendarPermissionManager,
    private val writeCalendarPermissionManager: WriteCalendarPermissionManager,
) :
    BaseViewModel<CalendarPermissionUiState, CalendarPermissionUserAction, CalendarPermissionUiEvent>() {

    private val _uiState: MutableStateFlow<CalendarPermissionUiState> =
        MutableStateFlow(CalendarPermissionUiState.Loading)
    override val uiState: StateFlow<CalendarPermissionUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<CalendarPermissionUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<CalendarPermissionUiEvent>
        get() = _uiEvent.asSharedFlow()

    init {
        observeReadCalendarPermissionState()
    }

    override fun performUserAction(action: CalendarPermissionUserAction) =
        when (action) {
            is CalendarPermissionUserAction.OnCalendarReadPermissionResultUserAction ->
                handleReadPermissionResult(action.isGranted)
            is CalendarPermissionUserAction.OnCalendarWritePermissionResultUserAction ->
                handleWritePermissionResult(action.isGranted)
            is CalendarPermissionUserAction.OnReturnBackFromSettingsUserAction ->
                handleReturnBackFromSettings()
        }

    private fun observeReadCalendarPermissionState() {
        viewModelScope.launch(dispatcherProvider.main()) {
            readCalendarPermissionManager.permissionState.collect { state ->
                when (state) {
                    PermissionManager.State.NOT_ASKED ->
                        _uiState.emit(
                            CalendarPermissionUiState.AskForReadCalendarPermission(
                                permission = readCalendarPermissionManager.permissionName
                            )
                        )
                    PermissionManager.State.SHOW_RATIONALE ->
                        _uiState.emit(
                            CalendarPermissionUiState.ShowRationaleForReadCalendarPermission(
                                permission = readCalendarPermissionManager.permissionName
                            )
                        )
                    PermissionManager.State.SHOW_SETTINGS ->
                        _uiState.emit(CalendarPermissionUiState.ShowSettings)
                    PermissionManager.State.GRANTED ->
                        observeWriteCalendarPermissionState()
                }
            }
        }
    }

    private fun observeWriteCalendarPermissionState() {
        viewModelScope.launch(dispatcherProvider.main()) {
            writeCalendarPermissionManager.permissionState.collect { state ->
                when (state) {
                    PermissionManager.State.NOT_ASKED ->
                        _uiState.emit(
                            CalendarPermissionUiState.AskForWriteCalendarPermission(
                                permission = readCalendarPermissionManager.permissionName
                            )
                        )
                    PermissionManager.State.SHOW_RATIONALE ->
                        _uiState.emit(
                            CalendarPermissionUiState.ShowRationaleForWriteCalendarPermission(
                                permission = readCalendarPermissionManager.permissionName
                            )
                        )
                    PermissionManager.State.SHOW_SETTINGS ->
                        _uiState.emit(CalendarPermissionUiState.ShowSettings)
                    PermissionManager.State.GRANTED ->
                        _uiEvent.emit(CalendarPermissionUiEvent.AllPermissionGranted)
                }
            }
        }
    }

    private fun handleReadPermissionResult(isGranted: Boolean) {
        viewModelScope.launch(dispatcherProvider.main()) {
            readCalendarPermissionManager.updatePermissionState(isGranted)
        }
    }

    private fun handleWritePermissionResult(isGranted: Boolean) {
        viewModelScope.launch(dispatcherProvider.main()) {
            writeCalendarPermissionManager.updatePermissionState(isGranted)
        }
    }

    private fun handleReturnBackFromSettings() {
        viewModelScope.launch(dispatcherProvider.main()) {
            val isReadCalendarGranted = readCalendarPermissionManager.isPermissionGranted()

            readCalendarPermissionManager.updatePermissionState(isReadCalendarGranted)
        }
    }

    sealed class CalendarPermissionUiState : UiState {

        data object Loading : CalendarPermissionUiState()

        data class AskForReadCalendarPermission(
            val permission: String,
        ) : CalendarPermissionUiState()

        data class ShowRationaleForReadCalendarPermission(
            val permission: String,
        ) : CalendarPermissionUiState()

        data class AskForWriteCalendarPermission(
            val permission: String,
        ) : CalendarPermissionUiState()

        data class ShowRationaleForWriteCalendarPermission(
            val permission: String,
        ) : CalendarPermissionUiState()

        data object ShowSettings : CalendarPermissionUiState()
    }

    sealed class CalendarPermissionUiEvent : UiEvent {

        data object AllPermissionGranted : CalendarPermissionUiEvent()
    }

    sealed class CalendarPermissionUserAction : UserAction {

        data class OnCalendarReadPermissionResultUserAction(
            val isGranted: Boolean,
        ) : CalendarPermissionUserAction()

        data class OnCalendarWritePermissionResultUserAction(
            val isGranted: Boolean,
        ) : CalendarPermissionUserAction()

        data object OnReturnBackFromSettingsUserAction : CalendarPermissionUserAction()
    }
}
