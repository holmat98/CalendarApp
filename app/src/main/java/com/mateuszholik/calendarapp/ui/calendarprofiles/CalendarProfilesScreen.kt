package com.mateuszholik.calendarapp.ui.calendarprofiles

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.calendarprofiles.CalendarProfilesViewModel.CalendarProfilesUserAction
import com.mateuszholik.calendarapp.ui.calendarprofiles.CalendarProfilesViewModel.CalendarProfilesUiState
import com.mateuszholik.calendarapp.ui.calendarprofiles.CalendarProfilesViewModel.CalendarProfilesUiEvent
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
import com.mateuszholik.uicomponents.checkbox.CommonCheckbox
import com.mateuszholik.uicomponents.extensions.shimmerEffect
import com.mateuszholik.uicomponents.text.HeadlineLargeText
import com.mateuszholik.uicomponents.text.TitleLargeText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarProfilesScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarProfilesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ChangeSystemBarColors(
        statusBarColor = MaterialTheme.colorScheme.surface,
        navigationBarColor = MaterialTheme.colorScheme.surface,
        darkTheme = !isSystemInDarkTheme()
    )

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

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.performUserAction(CalendarProfilesUserAction.OnCalendarsConfirmed)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
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
        Box(
            modifier = Modifier
                .width(MaterialTheme.sizing.extraBig)
                .height(MaterialTheme.sizing.big)
                .shimmerEffect()
        )

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
        item {
            HeadlineLargeText(
                textResId = R.string.calendar_profile_header,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        calendars.forEach { (accountName, calendars) ->
            item {
                TitleLargeText(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                    text = accountName,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            items(
                items = calendars,
                key = { it.id }
            ) { calendar ->
                CommonCheckbox(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.small)
                        .fillMaxWidth(),
                    isChecked = calendar.isVisible,
                    text = calendar.calendarName,
                    onChecked = { onCalendarChecked(calendar.id, it) }
                )
            }
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface(color = MaterialTheme.colorScheme.surface) {
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
        Surface(color = MaterialTheme.colorScheme.surface) {
            ShimmerContent(paddingValues = PaddingValues(MaterialTheme.spacing.normal))
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface(color = MaterialTheme.colorScheme.surface) {
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
        Surface(color = MaterialTheme.colorScheme.surface) {
            Content(
                paddingValues = PaddingValues(MaterialTheme.spacing.normal),
                calendars = CALENDARS,
                onCalendarChecked = { _, _ -> }
            )
        }
    }
}
