package com.mateuszholik.calendarapp.ui.editevent

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.CalendarSelected
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.CalendarSelectionDismissed
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.ExitAttempt
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.ExitAttemptCancelled
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.ExitAttemptConfirmed
import com.mateuszholik.calendarapp.ui.editevent.EditEventViewModel.EditEventUserAction.SelectCalendar
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.models.Generic
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.buttons.CommonSmallButton
import com.mateuszholik.uicomponents.calendar.CalendarItem
import com.mateuszholik.uicomponents.cards.SelectionCard
import com.mateuszholik.uicomponents.dialog.CommonAlertDialog
import com.mateuszholik.uicomponents.dialog.CommonDialog
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
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
        val paddingValues =  PaddingValues(
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
                    onCalendarPressed = { viewModel.performUserAction(SelectCalendar) }
                )
            }
            is EditEventUiState.Loading -> {

            }
        }
    }

    calendars?.let {
        CommonDialog(onDismissRequest = { viewModel.performUserAction(CalendarSelectionDismissed) }) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
                contentPadding = PaddingValues(MaterialTheme.spacing.normal),
            ) {
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
}

@Composable
private fun Content(
    editMode: EditEventUiState.EventDetails,
    onCalendarPressed: () -> Unit,
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
                onTextChanged = {},
                hint = stringResource(R.string.edit_event_provide_title),
                singleLine = true,
            )
        }
        item {
            TextFieldWithIcon(
                text = editMode.description.description,
                onTextChanged = {},
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
    }
}

@BigPhonePreview
@Composable
private fun EditModePreview() {
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
                    allDay = false,
                    eventColor = null,
                    location = "Poland",
                    calendar = PreviewConstants.CALENDAR_1,
                ),
                onCalendarPressed = {},
                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.normal),
            )
        }
    }
}
