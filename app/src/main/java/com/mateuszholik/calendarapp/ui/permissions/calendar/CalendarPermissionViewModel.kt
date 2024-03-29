package com.mateuszholik.calendarapp.ui.permissions.calendar

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.permissions.CalendarPermissionsManager
import com.mateuszholik.calendarapp.permissions.PermissionManager
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.calendarapp.ui.base.BaseViewModel
import com.mateuszholik.calendarapp.ui.permissions.calendar.models.CalendarPermissionUiEvent
import com.mateuszholik.calendarapp.ui.permissions.calendar.models.CalendarPermissionUiState
import com.mateuszholik.calendarapp.ui.permissions.calendar.models.CalendarPermissionUserAction
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
class CalendarPermissionViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val calendarPermissionManager: CalendarPermissionsManager,
) :
    BaseViewModel<CalendarPermissionUiState, CalendarPermissionUserAction, CalendarPermissionUiEvent>() {

    private val _uiState: MutableStateFlow<CalendarPermissionUiState> =
        MutableStateFlow(CalendarPermissionUiState.Loading)
    override val uiState: StateFlow<CalendarPermissionUiState>
        get() = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<CalendarPermissionUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<CalendarPermissionUiEvent>
        get() = _uiEvent.asSharedFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable, "Error on Calendar Permission screen")

        viewModelScope.launch(dispatcherProvider.main()) {
            _uiEvent.emit(CalendarPermissionUiEvent.Error)
        }
    }

    init {
        observeCalendarPermissionState()
    }

    override fun performUserAction(action: CalendarPermissionUserAction) =
        when (action) {
            is CalendarPermissionUserAction.OnCalendarPermissionResultUserAction ->
                handlePermissionResult(action.results)
            is CalendarPermissionUserAction.OnReturnBackFromSettingsUserAction ->
                handleReturnBackFromSettings()
        }

    private fun observeCalendarPermissionState() {
        viewModelScope.launch(dispatcherProvider.main() + errorHandler) {
            calendarPermissionManager.permissionState.collect { state ->
                when (state) {
                    is PermissionManager.State.AskForPermissions ->
                        _uiState.emit(
                            CalendarPermissionUiState.AskForCalendarPermissions(
                                permissions = state.permissions
                            )
                        )
                    PermissionManager.State.PermissionsGranted ->
                        _uiEvent.emit(CalendarPermissionUiEvent.AllPermissionsGranted)
                    is PermissionManager.State.ShowRationale ->
                        _uiState.emit(
                            CalendarPermissionUiState.ShowRationaleForCalendarPermissions(
                                permissions = state.permissions
                            )
                        )
                    is PermissionManager.State.ShowSettings ->
                        _uiState.emit(CalendarPermissionUiState.ShowSettings)
                }
            }
        }
    }

    private fun handlePermissionResult(results: Map<String, Boolean>) {
        viewModelScope.launch(dispatcherProvider.main() + errorHandler) {
            _uiState.emit(CalendarPermissionUiState.Loading)
            calendarPermissionManager.handlePermissionsResult(results)
        }
    }

    private fun handleReturnBackFromSettings() {
        viewModelScope.launch(dispatcherProvider.main() + errorHandler) {
            _uiState.emit(CalendarPermissionUiState.Loading)
            calendarPermissionManager.handleBackFromSettings()
        }
    }
}
