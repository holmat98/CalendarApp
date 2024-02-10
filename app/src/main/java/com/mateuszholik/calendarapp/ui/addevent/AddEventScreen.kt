package com.mateuszholik.calendarapp.ui.addevent

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.addevent.models.AddEventUiState
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
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.switches.CommonSwitch
import com.mateuszholik.uicomponents.text.BodyMediumText
import com.mateuszholik.uicomponents.text.TextWithIcon
import com.mateuszholik.uicomponents.textfield.CommonOutlinedTextField
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onBackPressed: () -> Unit,
    viewModel: AddEventScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChangeSystemBarColors()

    ObserveAsEvents(flow = viewModel.uiEvent) { uiEvent ->
        println(uiEvent)
    }

    CommonScaffold(
        navigationIcon = {
            CommonIconButton(
                imageVector = Icons.Default.ArrowBack,
                onClick = onBackPressed,
            )
        },
        actions = {
            CommonSmallButton(
                textResId = R.string.button_save,
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
            onAllDaySelectionChanged = { allDay ->
                viewModel.performUserAction(
                    AllDaySelectionChanged(allDay)
                )
            }
        )
    }

}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    addEventUiState: AddEventUiState,
    onAllDaySelectionChanged: (Boolean) -> Unit,
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
                onTextChanged = {},
                hint = stringResource(R.string.edit_event_provide_title),
                focusRequester = focusRequester,
                singleLine = true
            )
        }

        item {
            SelectionCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
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
                        onAllDaySelectionChanged(it)
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
                            focusManager.clearFocus()
                        }
                    )
                    CommonDatePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventUiState.endDate.toLocalDate(),
                        onDateSelected = {
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
                            focusManager.clearFocus()
                        }
                    )
                    CommonDateTimePicker(
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.spacing.small)
                            .fillMaxWidth(),
                        date = addEventUiState.endDate,
                        onDateSelected = {
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
                onTextChanged = {},
                hint = stringResource(R.string.edit_event_provide_description),
                focusRequester = focusRequester,
            )
        }

        item {
            SelectionCard(onClick = { focusManager.clearFocus() }) {
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
                onTextChanged = {},
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
                onAllDaySelectionChanged = {},
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
                onAllDaySelectionChanged = {},
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
                onAllDaySelectionChanged = {},
            )
        }
    }
}
