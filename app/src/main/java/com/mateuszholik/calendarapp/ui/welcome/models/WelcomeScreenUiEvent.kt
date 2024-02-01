package com.mateuszholik.calendarapp.ui.welcome.models

import com.mateuszholik.calendarapp.ui.base.UiEvent

sealed class WelcomeScreenUiEvent : UiEvent {

    data object NavigateToNextScreen : WelcomeScreenUiEvent()

    data object NavigateToPermissionsScreen : WelcomeScreenUiEvent()
}
