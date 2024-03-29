package com.mateuszholik.calendarapp.ui.selectcalendars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.selectcalendars.models.CalendarProfilesUserAction
import com.mateuszholik.calendarapp.ui.selectcalendars.models.CalendarProfilesUiState
import com.mateuszholik.calendarapp.ui.selectcalendars.models.CalendarProfilesUiEvent
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.utils.PreviewConstants.CALENDARS
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.uicomponents.buttons.CommonIconButton
import com.mateuszholik.uicomponents.checkbox.CommonCheckbox
import com.mateuszholik.uicomponents.checkbox.CommonCheckboxDefaults
import com.mateuszholik.uicomponents.extensions.shimmerEffect
import com.mateuszholik.uicomponents.scaffold.CommonScaffold
import com.mateuszholik.uicomponents.text.TitleMediumText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarsSelectionScreen(
    onBackPressed: () -> Unit,
    viewModel: CalendarsSelectionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChangeSystemBarColors()

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when (event) {
            is CalendarProfilesUiEvent.CalendarsSelected -> onBackPressed()
            is CalendarProfilesUiEvent.Error -> {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = context.getString(R.string.error_unknown_text),
                        withDismissAction = true,
                    )
                }
            }
        }
    }

    CommonScaffold(
        navigationIcon = {
            CommonIconButton(
                imageVector = Icons.Default.ArrowBack,
                onClick = { viewModel.performUserAction(CalendarProfilesUserAction.OnCalendarsConfirmed) }
            )
        },
        title = {
            TitleMediumText(textResId = R.string.calendar_selection_header)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        val paddingValues = PaddingValues(
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
            start = MaterialTheme.spacing.normal,
            end = MaterialTheme.spacing.normal
        )

        when (uiState) {
            is CalendarProfilesUiState.Calendars -> {
                val calendars = (uiState as CalendarProfilesUiState.Calendars).calendars

                Content(
                    paddingValues = paddingValues,
                    calendars = calendars,
                    onCalendarChecked = { id, isChecked ->
                        viewModel.performUserAction(
                            CalendarProfilesUserAction.OnCalendarSelectionChanged(id, isChecked)
                        )
                    }
                )
            }
            is CalendarProfilesUiState.Loading -> ShimmerContent(paddingValues = paddingValues)
        }
    }
}

@Composable
private fun ShimmerContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
                    .width(MaterialTheme.sizing.big)
                    .height(MaterialTheme.sizing.normal)
                    .shimmerEffect()
            )
            repeat(2) {
                Row(modifier = Modifier.padding(top = MaterialTheme.spacing.small)) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.spacing.small)
                            .size(MaterialTheme.sizing.normal)
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .width(MaterialTheme.sizing.big)
                            .height(MaterialTheme.sizing.normal)
                            .shimmerEffect()
                    )
                }
            }
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    calendars: Map<String, List<Calendar>>,
    onCalendarChecked: (id: Long, isChecked: Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        calendars.forEach { (accountName, calendars) ->
            item {
                TitleMediumText(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                    text = accountName,
                )
            }

            items(
                items = calendars,
                key = { it.id }
            ) { calendar ->
                var isChecked by remember { mutableStateOf(calendar.isVisible) }

                CommonCheckbox(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    isChecked = isChecked,
                    text = calendar.calendarName,
                    onChecked = {
                        isChecked = it
                        onCalendarChecked(calendar.id, it)
                    },
                    colors = CommonCheckboxDefaults.colors(
                        checkedColor = calendar.color?.let { Color(it) } ?: MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = { _, _ -> }
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun ShimmerEffectPreview() {
    CalendarAppTheme(styleType = StyleType.SPRING, darkTheme = true) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            ShimmerContent(paddingValues = PaddingValues(MaterialTheme.spacing.normal))
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = { _, _ -> }
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = { _, _ -> }
            )
        }
    }
}
