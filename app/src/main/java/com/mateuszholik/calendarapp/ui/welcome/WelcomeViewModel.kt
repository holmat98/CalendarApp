package com.mateuszholik.calendarapp.ui.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseStateViewModel
import com.mateuszholik.calendarapp.ui.base.UiState
import com.mateuszholik.calendarapp.ui.welcome.provider.WelcomeScreenInfoProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    welcomeScreenInfoProvider: WelcomeScreenInfoProvider,
) : BaseStateViewModel<WelcomeViewModel.WelcomeScreenState>() {

    private val _state: MutableStateFlow<WelcomeScreenState> =
        MutableStateFlow(welcomeScreenInfoProvider.provide())
    override val state: StateFlow<WelcomeScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            delay(2.seconds)
            _state.emit(WelcomeScreenState.NextScreen)
        }
    }

    sealed class WelcomeScreenState : UiState {

        data class WelcomeInfo(
            @StringRes val text: Int,
            @DrawableRes val image: Int,
        ) : WelcomeScreenState()

        data object NextScreen : WelcomeScreenState()
    }
}
