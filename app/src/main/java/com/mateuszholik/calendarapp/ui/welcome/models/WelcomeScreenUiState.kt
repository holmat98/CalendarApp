package com.mateuszholik.calendarapp.ui.welcome.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.mateuszholik.calendarapp.ui.base.UiState

@Stable
data class WelcomeScreenUiState(
    @StringRes val text: Int,
    @DrawableRes val image: Int,
) : UiState
