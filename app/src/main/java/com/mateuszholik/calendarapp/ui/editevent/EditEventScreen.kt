package com.mateuszholik.calendarapp.ui.editevent

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUiEvent
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUiState
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.AllDaySelectionChanged
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.CalendarSelected
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.CalendarSelectionDismissed
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.EndDateChanged
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.ExitAttempt
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.ExitAttemptCancelled
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.ExitAttemptConfirmed
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.SelectCalendar
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.SelectEventColor
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.SelectEventColorDismissed
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.SelectedEventColor
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.StartDateChanged
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.UpdateDescription
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.UpdateLocation
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.UpdateTitle
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Generic
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.buttons.CommonSmallButton
import com.mateuszholik.uicomponents.calendar.CalendarItem
import com.mateuszholik.uicomponents.cards.SelectionCard
import com.mateuszholik.uicomponents.color.ColorItem
import com.mateuszholik.uicomponents.date.CommonDatePicker
import com.mateuszholik.uicomponents.date.CommonDateTimePicker
import com.mateuszholik.uicomponents.dialog.CommonAlertDialog
import com.mateuszholik.uicomponents.dialog.CommonDialog
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.switches.CommonSwitch
import com.mateuszholik.uicomponents.text.BodyMediumText
import com.mateuszholik.uicomponents.text.TextWithIcon
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.textfield.CommonOutlinedTextField
import com.mateuszholik.uicomponents.textfield.TextFieldWithIcon
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventScreen(
    onBackPressed: () -> Unit,
    viewModel: EditEventViewModel = hiltViewModel(),
) {
    var calendars by remember { mutableStateOf<List<Calendar>?>(null) }
    var shouldShowExitDialog by remember { mutableStateOf(false) }
    var colors by remember { mutableStateOf<List<ColorsProvider.ColorInfo>?>(null) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is EditEventUiEvent.DismissCalendarSelection -> {
                calendars = null
            }
            is EditEventUiEvent.Error -> {

            }
            is EditEventUiEvent.ShowCalendarSelection -> {
                calendars = uiEvent.calendars
            }
            is EditEventUiEvent.DismissExitDialog -> {
                shouldShowExitDialog = false
            }
            is EditEventUiEvent.NavigateBack -> onBackPressed()
            is EditEventUiEvent.ShowExitDialog -> {
                shouldShowExitDialog = true
            }
            is EditEventUiEvent.DismissColorEventSelection -> {
                colors = null
            }
            is EditEventUiEvent.ShowEventColorSelection -> {
                colors = uiEvent.colors
            }
        }
    }

    BackHandler { viewModel.performUserAction(ExitAttempt) }

    CommonScaffold(
        navigationIcon = {
            CommonIconButton(
                imageVector = Icons.Default.Close,
                onClick = { viewModel.performUserAction(ExitAttempt) }
            )
        },
        actions = {
            CommonSmallButton(
                modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                textResId = R.string.button_save, onClick = {}
            )
        }
    ) {
        val paddingValues = PaddingValues(
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
            end = MaterialTheme.spacing.normal,
            start = MaterialTheme.spacing.normal
        )

        when (uiState) {
            is EditEventUiState.EventDetails -> {
                Content(
                    paddingValues = paddingValues,
                    editMode = uiState as EditEventUiState.EventDetails,
                    onCalendarPressed = { viewModel.performUserAction(SelectCalendar) },
                    onColorPressed = { viewModel.performUserAction(SelectEventColor) },
                    onTitleChanged = { newTitle -> viewModel.performUserAction(UpdateTitle(newTitle)) },
                    onDescriptionChanged = { newDescription ->
                        viewModel.performUserAction(UpdateDescription(newDescription))
                    },
                    onLocationChanged = { newLocation ->
                        viewModel.performUserAction(UpdateLocation(newLocation))
                    },
                    onAllDayChanged = { allDay ->
                        viewModel.performUserAction(AllDaySelectionChanged(allDay))
                    },
                    onStartDateChanged = { newStartDate ->
                        viewModel.performUserAction(StartDateChanged(newStartDate))
                    },
                    onEndDateChanged = { newEndDate ->
                        viewModel.performUserAction(EndDateChanged(newEndDate))
                    }
                )
            }
            is EditEventUiState.Loading -> {

            }
        }
    }

    calendars?.let {
        CommonDialog(onDismissRequest = { viewModel.performUserAction(CalendarSelectionDismissed) }) {
            item {
                TitleMediumText(textResId = R.string.edit_event_select_calendar)
            }
            items(items = it) { calendar ->
                with(calendar) {
                    CalendarItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.performUserAction(CalendarSelected(calendar)) },
                        email = accountName,
                        name = calendarName,
                        calendarColor = color
                    )
                }
            }
        }
    }

    if (shouldShowExitDialog) {
        CommonAlertDialog(
            icon = Icons.Filled.ExitToApp,
            titleResId = R.string.edit_event_exit_title,
            messageResId = R.string.edit_event_exit_message,
            negativeButtonResId = R.string.button_cancel,
            positiveButtonResId = R.string.button_ok,
            onNegativeButtonClicked = { viewModel.performUserAction(ExitAttemptCancelled) },
            onPositiveButtonClicked = { viewModel.performUserAction(ExitAttemptConfirmed) }
        )
    }

    colors?.let {
        CommonDialog(onDismissRequest = { viewModel.performUserAction(SelectEventColorDismissed) }) {
            item {
                TitleMediumText(textResId = R.string.edit_event_select_event_color)
            }

            items(items = it) { color ->
                ColorItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.performUserAction(SelectedEventColor(color)) },
                    color = color.value,
                    name = stringResource(color.name)
                )
            }
        }
    }
}

@Composable
private fun Content(
    editMode: EditEventUiState.EventDetails,
    onCalendarPressed: () -> Unit,
    onColorPressed: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit = {},
    onEndDateChanged: (LocalDateTime) -> Unit = {},
    onAllDayChanged: (Boolean) -> Unit = {},
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.normal)
    ) {
        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = editMode.title,
                onTextChanged = onTitleChanged,
                hint = stringResource(R.string.edit_event_provide_title),
                singleLine = true,
            )
        }
        item {
            TextFieldWithIcon(
                text = editMode.description.description,
                onTextChanged = onDescriptionChanged,
                icon = Icons.Default.List,
                hint = stringResource(R.string.edit_event_provide_description),
                singleLine = false,
            )
        }
        item { Divider() }

        editMode.calendar?.let { calendar ->
            item {
                with(calendar) {
                    SelectionCard(onClick = onCalendarPressed) {
                        CalendarItem(
                            email = accountName,
                            name = calendarName,
                            calendarColor = color
                        )
                    }
                }
            }
        }

        item { Divider() }

        item {
            Column {
                CommonSwitch(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    text = stringResource(R.string.edit_event_all_day),
                    isSelected = editMode.allDay,
                    onSelectionChanged = onAllDayChanged
                )
                if (editMode.allDay) {
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = editMode.dateStart.toLocalDate(),
                        onDateSelected = { onStartDateChanged(it.atStartOfDay()) }
                    )
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = editMode.dateEnd.toLocalDate(),
                        onDateSelected = { onEndDateChanged(it.atStartOfDay()) }
                    )
                } else {
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = editMode.dateStart,
                        onDateSelected = onStartDateChanged
                    )
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = editMode.dateEnd,
                        onDateSelected = onEndDateChanged
                    )
                }
                TextWithIcon(
                    text = editMode.timezone,
                    icon = Icons.Outlined.DateRange
                )
            }
        }

        item { Divider() }

        item {
            SelectionCard(onClick = onColorPressed) {
                if (editMode.eventColor == null) {
                    BodyMediumText(textResId = R.string.edit_event_select_event_color)
                } else {
                    ColorItem(
                        color = editMode.eventColor.value,
                        name = stringResource(editMode.eventColor.name)
                    )
                }
            }
        }
        item {
            TextFieldWithIcon(
                text = editMode.location,
                onTextChanged = onLocationChanged,
                icon = Icons.Outlined.LocationOn,
                hint = stringResource(R.string.edit_event_provide_location),
                singleLine = false,
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun EditModePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = false) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                editMode = EditEventUiState.EventDetails(
                    id = 1,
                    title = "Event 1",
                    description = Generic("Description"),
                    dateStart = LocalDateTime.of(2024, 1, 14, 12, 0, 0),
                    dateEnd = LocalDateTime.of(2024, 1, 14, 13, 0, 0),
                    timezone = "Timezone",
                    allDay = false,
                    eventColor = ColorsProvider.ColorInfo(-1466246, R.string.color_light_coral),
                    location = "Poland",
                    calendar = PreviewConstants.CALENDAR_1,
                ),
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.normal),
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun EditModePreview2() {
    CalendarAppTheme(styleType = StyleType.SUMMER, darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                editMode = EditEventUiState.EventDetails(
                    id = 1,
                    title = "Event 1",
                    description = Generic("Description"),
                    dateStart = LocalDateTime.of(2024, 1, 14, 12, 0, 0),
                    dateEnd = LocalDateTime.of(2024, 1, 14, 13, 0, 0),
                    timezone = "Timezone",
                    allDay = true,
                    eventColor = null,
                    location = "Poland",
                    calendar = PreviewConstants.CALENDAR_1,
                ),
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.normal),
            )
        }
    }
}
