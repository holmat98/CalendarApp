package com.mateuszholik.calendarapp.ui.addevent.models

import com.mateuszholik.calendarapp.ui.base.UiEvent
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.dateutils.Minutes
import com.mateuszholik.domain.models.Calendar
import java.util.TimeZone

sealed class AddEventUiEvent : UiEvent {

    data object Error : AddEventUiEvent()

    data object DismissCalendarSelection : AddEventUiEvent()

    data object DismissColorEventSelection : AddEventUiEvent()

    data object DismissReminderSelection : AddEventUiEvent()

    data object DismissTimeZoneSelection : AddEventUiEvent()

    data class ShowCalendarSelection(val calendars: List<Calendar>) : AddEventUiEvent()

    data class ShowEventColorSelection(
        val colors: List<ColorsProvider.ColorInfo>,
    ) : AddEventUiEvent()

    data class ShowReminderSelection(val minutes: List<Minutes>) : AddEventUiEvent()

    data class ShowTimeZoneSelection(
        val timezones: List<TimeZone>
    ) : AddEventUiEvent()

    data object NavigateBack : AddEventUiEvent()
}
