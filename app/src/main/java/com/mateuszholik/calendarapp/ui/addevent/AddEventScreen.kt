package com.mateuszholik.calendarapp.ui.addevent

import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiEvent
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiState
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUserAction
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUserAction.AllDaySelectionChanged
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.provider.ColorsProvider
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.buttons.CommonSmallButton
import com.mateuszholik.uicomponents.calendar.CalendarItem
import com.mateuszholik.uicomponents.cards.SelectionCard
import com.mateuszholik.uicomponents.color.ColorItem
import com.mateuszholik.uicomponents.date.CommonDatePicker
import com.mateuszholik.uicomponents.date.CommonDateTimePicker
import com.mateuszholik.uicomponents.dialog.CommonDialog
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.switches.CommonSwitch
import com.mateuszholik.uicomponents.text.BodyMediumText
import com.mateuszholik.uicomponents.text.TextWithIcon
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.textfield.CommonOutlinedTextField
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onBackPressed: () -> Unit,
    viewModel: AddEventScreenViewModel = hiltViewModel(),
) {
    var calendars by remember { mutableStateOf<List<Calendar>?>(null) }
    var colors by remember { mutableStateOf<List<ColorsProvider.ColorInfo>?>(null) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    ChangeSystemBarColors()

    ObserveAsEvents(flow = viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is AddEventUiEvent.DismissCalendarSelection -> {
                calendars = null
            }
            is AddEventUiEvent.DismissColorEventSelection -> {
                colors = null
            }
            is AddEventUiEvent.Error -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = context.getString(R.string.error_unknown_text),
                        withDismissAction = true,
                    )
                }
            }
            is AddEventUiEvent.NavigateBack -> {
                onBackPressed()
            }
            is AddEventUiEvent.ShowCalendarSelection -> {
                calendars = uiEvent.calendars
            }
            is AddEventUiEvent.ShowEventColorSelection -> {
                colors = uiEvent.colors
            }
        }
    }

    CommonScaffold(
        navigationIcon = {
            CommonIconButton(
                imageVector = Icons.Default.ArrowBack,
                onClick = { viewModel.performUserAction(AddEventUserAction.ExitAttempt) },
            )
        },
        actions = {
            CommonSmallButton(
                modifier = Modifier.padding(end = MaterialTheme.spacing.small),
                textResId = R.string.button_save,
                isEnabled = isButtonEnabled,
                onClick = {}
            )
        }
    ) {
        val paddingValues = PaddingValues(
            start = MaterialTheme.spacing.normal,
            end = MaterialTheme.spacing.normal,
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
        )

        Content(
            paddingValues = paddingValues,
            addEventUiState = uiState,
            onAllDayChanged = { allDay ->
                viewModel.performUserAction(
                    AllDaySelectionChanged(allDay)
                )
            },
            onCalendarPressed = { viewModel.performUserAction(AddEventUserAction.SelectCalendar) },
            onColorPressed = { viewModel.performUserAction(AddEventUserAction.SelectEventColor) },
            onTitleChanged = { newTitle ->
                viewModel.performUserAction(
                    AddEventUserAction.UpdateTitle(newTitle)
                )
            },
            onDescriptionChanged = { newDescription ->
                viewModel.performUserAction(AddEventUserAction.UpdateDescription(newDescription))
            },
            onLocationChanged = { newLocation ->
                viewModel.performUserAction(AddEventUserAction.UpdateLocation(newLocation))
            },
            onStartDateChanged = { newStartDate ->
                viewModel.performUserAction(AddEventUserAction.StartDateChanged(newStartDate))
            },
            onEndDateChanged = { newEndDate ->
                viewModel.performUserAction(AddEventUserAction.EndDateChanged(newEndDate))
            }
        )
    }

    calendars?.let {
        CommonDialog(
            onDismissRequest = {
                viewModel.performUserAction(AddEventUserAction.CalendarSelectionDismissed)
            }
        ) {
            item {
                TitleMediumText(textResId = R.string.edit_event_select_calendar)
            }
            items(items = it) { calendar ->
                with(calendar) {
                    CalendarItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.performUserAction(
                                    AddEventUserAction.CalendarSelected(calendar)
                                )
                            },
                        email = accountName,
                        name = calendarName,
                        calendarColor = color
                    )
                }
            }
        }
    }

    colors?.let {
        CommonDialog(
            onDismissRequest = {
                viewModel.performUserAction(AddEventUserAction.SelectEventColorDismissed)
            }
        ) {
            item {
                TitleMediumText(textResId = R.string.edit_event_select_event_color)
            }

            items(items = it) { color ->
                ColorItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.performUserAction(
                                AddEventUserAction.SelectedEventColor(color)
                            )
                        },
                    color = color.value,
                    name = stringResource(color.name)
                )
            }
        }
    }

}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    addEventUiState: AddEventUiState,
    onCalendarPressed: () -> Unit,
    onColorPressed: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit = {},
    onEndDateChanged: (LocalDateTime) -> Unit = {},
    onAllDayChanged: (Boolean) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.normal),
    ) {
        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventUiState.title,
                onTextChanged = onTitleChanged,
                hint = stringResource(R.string.edit_event_provide_title),
                focusRequester = focusRequester,
                singleLine = true
            )
        }

        item {
            SelectionCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onCalendarPressed()
                    focusManager.clearFocus()
                }
            ) {
                with(addEventUiState.calendar) {
                    CalendarItem(
                        email = accountName,
                        name = calendarName,
                        calendarColor = color
                    )
                }
            }
        }

        item { Divider() }

        item {
            Column(modifier = Modifier.animateContentSize()) {
                CommonSwitch(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    text = stringResource(R.string.edit_event_all_day),
                    isSelected = addEventUiState.allDay,
                    onSelectionChanged = {
                        onAllDayChanged(it)
                        focusManager.clearFocus()
                    }
                )
                if (addEventUiState.allDay) {
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventUiState.startDate.toLocalDate(),
                        onDateSelected = {
                            onStartDateChanged(it.atStartOfDay())
                            focusManager.clearFocus()
                        }
                    )
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventUiState.endDate.toLocalDate(),
                        onDateSelected = {
                            onEndDateChanged(it.atStartOfDay())
                            focusManager.clearFocus()
                        }
                    )
                    TextWithIcon(
                        text = addEventUiState.timezone,
                        icon = Icons.Outlined.DateRange
                    )
                } else {
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventUiState.startDate,
                        onDateSelected = {
                            onStartDateChanged(it)
                            focusManager.clearFocus()
                        }
                    )
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventUiState.endDate,
                        onDateSelected = {
                            onEndDateChanged(it)
                            focusManager.clearFocus()
                        }
                    )
                    SelectionCard(onClick = { focusManager.clearFocus() }) {
                        BodyMediumText(text = addEventUiState.timezone)
                    }
                }
            }
        }

        item { Divider() }

        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventUiState.description,
                onTextChanged = onDescriptionChanged,
                hint = stringResource(R.string.edit_event_provide_description),
                focusRequester = focusRequester,
            )
        }

        item {
            SelectionCard(
                onClick = {
                    onColorPressed()
                    focusManager.clearFocus()
                }
            ) {
                ColorItem(
                    color = addEventUiState.color.value,
                    name = stringResource(addEventUiState.color.name),
                )
            }
        }

        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventUiState.urls,
                onTextChanged = {},
                hint = stringResource(R.string.add_event_provide_urls),
                focusRequester = focusRequester,
            )
        }

        item {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = addEventUiState.location,
                onTextChanged = onLocationChanged,
                hint = stringResource(R.string.edit_event_provide_location),
                focusRequester = focusRequester,
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = 0.dp
                ),
                addEventUiState = AddEventUiState(
                    title = "Title",
                    description = "Description",
                    allDay = false,
                    startDate = LocalDateTime.of(2024, 2, 10, 15, 0, 0),
                    endDate = LocalDateTime.of(2024, 2, 10, 16, 0, 0),
                    timezone = "Europe/Warsaw",
                    urls = "",
                    calendar = Calendar(
                        id = 1,
                        accountName = "test",
                        calendarName = "test",
                        isVisible = true,
                        color = null
                    ),
                    color = ColorsProvider.ColorInfo(
                        value = Color.Magenta.toArgb(),
                        name = R.string.color_current,
                    ),
                    location = "Poland",
                ),
                onAllDayChanged = {},
                onStartDateChanged = {},
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview2() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = 0.dp
                ),
                addEventUiState = AddEventUiState(
                    title = "Title",
                    description = "Description",
                    allDay = false,
                    startDate = LocalDateTime.of(2024, 2, 10, 15, 0, 0),
                    endDate = LocalDateTime.of(2024, 2, 10, 16, 0, 0),
                    timezone = "Europe/Warsaw",
                    urls = "",
                    calendar = Calendar(
                        id = 1,
                        accountName = "test",
                        calendarName = "test",
                        isVisible = true,
                        color = null
                    ),
                    color = ColorsProvider.ColorInfo(
                        value = Color.Magenta.toArgb(),
                        name = R.string.color_current,
                    ),
                    location = "Poland",
                ),
                onAllDayChanged = {},
                onStartDateChanged = {},
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun Preview3() {
    CalendarAppTheme(styleType = StyleType.WINTER, darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal,
                    vertical = 0.dp
                ),
                addEventUiState = AddEventUiState(
                    title = "Title",
                    description = "Description",
                    allDay = true,
                    startDate = LocalDateTime.of(2024, 2, 10, 0, 0, 0),
                    endDate = LocalDateTime.of(2024, 2, 10, 0, 0, 0),
                    timezone = "UTC",
                    urls = "",
                    calendar = Calendar(
                        id = 1,
                        accountName = "test",
                        calendarName = "test",
                        isVisible = true,
                        color = null
                    ),
                    color = ColorsProvider.ColorInfo(
                        value = Color.Magenta.toArgb(),
                        name = R.string.color_current,
                    ),
                    location = "Poland",
                ),
                onAllDayChanged = {},
                onStartDateChanged = {},
                onCalendarPressed = {},
                onColorPressed = {},
                onTitleChanged = {},
                onDescriptionChanged = {},
                onLocationChanged = {},
            )
        }
    }
}
