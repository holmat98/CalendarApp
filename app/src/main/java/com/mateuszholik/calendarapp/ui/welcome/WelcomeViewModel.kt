package com.mateuszholik.calendarapp.ui.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseStateViewModel
import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.welcome.provider.WelcomeScreenInfoProvider
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenUiEvent
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
        viewModelScope.launch {
            delay(2.seconds)
            _uiEvent.emit(WelcomeScreenUiEvent.NavigateToNextScreen)
        }
    }

    data class WelcomeScreenUiState(
        @StringRes val text: Int,
        @DrawableRes val image: Int,
    ) : UiState

    sealed class WelcomeScreenUiEvent : UiEvent {

        data object NavigateToNextScreen : WelcomeScreenUiEvent()
    }
}
