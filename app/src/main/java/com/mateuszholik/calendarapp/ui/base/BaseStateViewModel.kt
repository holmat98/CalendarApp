package com.mateuszholik.calendarapp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseStateViewModel<T: UiState, R: UiEvent> : ViewModel() {

    abstract val uiState: StateFlow<T>

    abstract val uiEvent: SharedFlow<R>
}
