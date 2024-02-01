package com.mateuszholik.calendarapp.ui.editevent.models

import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.domain.models.Calendar

sealed class EditEventUiEvent : UiEvent {

    data object Error : EditEventUiEvent()

    data object DismissCalendarSelection : EditEventUiEvent()

    data class ShowCalendarSelection(val calendars: List<Calendar>) : EditEventUiEvent()

    data object ShowExitDialog : EditEventUiEvent()

    data object DismissExitDialog : EditEventUiEvent()

    data object NavigateBack : EditEventUiEvent()

    data object DismissColorEventSelection : EditEventUiEvent()

    data class ShowEventColorSelection(
        val colors: List<ColorsProvider.ColorInfo>,
    ) : EditEventUiEvent()

    data class ChangeSaveButtonAvailability(val enabled: Boolean) : EditEventUiEvent()
}
