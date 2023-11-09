package com.mateuszholik.calendarapp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T: UiState, R: UserAction, E: UiEvent> : ViewModel() {

    abstract val uiState: StateFlow<T>

    abstract val uiEvent: SharedFlow<E>

    abstract fun performUserAction(action: R)
}
