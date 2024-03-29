package com.mateuszholik.calendarapp.ui.welcome

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.permissions.CalendarPermissionsManager
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.calendarapp.ui.base.BaseStateViewModel
import com.mateuszholik.calendarapp.ui.welcome.models.WelcomeScreenUiEvent
import com.mateuszholik.calendarapp.ui.welcome.models.WelcomeScreenUiState
import com.mateuszholik.calendarapp.ui.welcome.provider.WelcomeScreenInfoProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val calendarPermissionsManager: CalendarPermissionsManager,
    dispatcherProvider: DispatcherProvider,
    welcomeScreenInfoProvider: WelcomeScreenInfoProvider,
) : BaseStateViewModel<WelcomeScreenUiState, WelcomeScreenUiEvent>() {

    private val _uiState: MutableStateFlow<WelcomeScreenUiState> =
        MutableStateFlow(welcomeScreenInfoProvider.provide())
    override val uiState: StateFlow<WelcomeScreenUiState>
        get() = _uiState

    private val _uiEvent: MutableSharedFlow<WelcomeScreenUiEvent> = MutableSharedFlow()
    override val uiEvent: SharedFlow<WelcomeScreenUiEvent>
        get() = _uiEvent

    init {
        viewModelScope.launch(dispatcherProvider.main()) {
            val arePermissionsGranted = async {
                calendarPermissionsManager.arePermissionsGranted()
            }

            delay(2.seconds)
            if (arePermissionsGranted.await()) {
                _uiEvent.emit(WelcomeScreenUiEvent.NavigateToNextScreen)
            } else {
                _uiEvent.emit(WelcomeScreenUiEvent.NavigateToPermissionsScreen)
            }
        }
    }
}
