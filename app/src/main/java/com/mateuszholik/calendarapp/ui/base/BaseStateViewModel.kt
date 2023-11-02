package com.mateuszholik.calendarapp.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseStateViewModel<T: UiState> : ViewModel() {

    abstract val state: Flow<T>
}
