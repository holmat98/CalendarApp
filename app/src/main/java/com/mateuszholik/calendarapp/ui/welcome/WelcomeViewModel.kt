package com.mateuszholik.calendarapp.ui.welcome

import androidx.lifecycle.viewModelScope
import com.mateuszholik.calendarapp.ui.base.BaseStateViewModel
import com.mateuszholik.calendarapp.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class WelcomeViewModel @Inject constructor() :
    BaseStateViewModel<WelcomeViewModel.WelcomeScreenState>() {

    private val _state: MutableStateFlow<WelcomeScreenState> =
        MutableStateFlow(WelcomeScreenState.WelcomeInfo(0, 0))
    override val state: Flow<WelcomeScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            delay(300.milliseconds)
            _state.emit(WelcomeScreenState.NextScreen)
        }
    }

    sealed class WelcomeScreenState : UiState {

        data class WelcomeInfo(
            val text: Int,
            val image: Int,
        ) : WelcomeScreenState()

        data object NextScreen : WelcomeScreenState()
    }
}
