package com.mateuszholik.calendarapp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseStateViewModel<T: UiState> : ViewModel() {

    abstract val state: StateFlow<T>
}
