package com.mateuszholik.calendarapp.ui.addevent.models

import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.domain.models.Calendar

sealed class AddEventUiEvent : UiEvent {

    data object Error : AddEventUiEvent()

    data object DismissCalendarSelection : AddEventUiEvent()

    data class ShowCalendarSelection(val calendars: List<Calendar>) : AddEventUiEvent()

    data object NavigateBack : AddEventUiEvent()

    data object DismissColorEventSelection : AddEventUiEvent()

    data class ShowEventColorSelection(
        val colors: List<ColorsProvider.ColorInfo>,
    ) : AddEventUiEvent()
}