package com.mateuszholik.calendarapp.ui

import androidx.lifecycle.ViewModel
import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import com.mateuszholik.calendarapp.ui.theme.provider.StyleProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val styleProvider: StyleProvider,
): ViewModel() {

    val currentStyle: StyleType
        get() = styleProvider.provide()
}
