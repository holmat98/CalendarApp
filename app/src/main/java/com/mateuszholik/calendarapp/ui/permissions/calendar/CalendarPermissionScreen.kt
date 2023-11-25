package com.mateuszholik.calendarapp.ui.permissions.calendar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.HeadlineMediumText
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.text.TitleSmallText

@Composable
fun CalendarPermissionScreen(
    onPermissionGranted: () -> Unit,
    viewModel: CalendarPermissionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(containerColor = MaterialTheme.colorScheme.surface) {
        when (uiState) {
            is CalendarPermissionViewModel.CalendarPermissionUiState.AskForReadCalendarPermission -> {
                Content(
                    paddingValues = it,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.welcome_screen_hello_december,
                    messageResId = R.string.calendar_screen_no_events_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            is CalendarPermissionViewModel.CalendarPermissionUiState.AskForWriteCalendarPermission -> {
                Content(
                    paddingValues = it,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.welcome_screen_hello_december,
                    messageResId = R.string.calendar_screen_no_events_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            CalendarPermissionViewModel.CalendarPermissionUiState.Loading -> {
                Content(
                    paddingValues = it,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.welcome_screen_hello_december,
                    messageResId = R.string.calendar_screen_no_events_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            is CalendarPermissionViewModel.CalendarPermissionUiState.ShowRationaleForReadCalendarPermission -> {
                Content(
                    paddingValues = it,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.welcome_screen_hello_december,
                    messageResId = R.string.calendar_screen_no_events_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            is CalendarPermissionViewModel.CalendarPermissionUiState.ShowRationaleForWriteCalendarPermission -> {
                Content(
                    paddingValues = it,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.welcome_screen_hello_december,
                    messageResId = R.string.calendar_screen_no_events_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            CalendarPermissionViewModel.CalendarPermissionUiState.ShowSettings -> {
                Content(
                    paddingValues = it,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.welcome_screen_hello_december,
                    messageResId = R.string.calendar_screen_no_events_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
        }
    }
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    @DrawableRes imageResId: Int,
    @StringRes titleResId: Int,
    @StringRes messageResId: Int,
    @StringRes buttonResId: Int,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier.size(MaterialTheme.sizing.extraBig),
                painter = painterResource(imageResId),
                contentDescription = null
            )
            HeadlineMediumText(
                modifier = Modifier.padding(top = MaterialTheme.spacing.normal),
                textResId = titleResId,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            TitleMediumText(
                modifier = Modifier.padding(top = MaterialTheme.spacing.normal),
                textResId = messageResId,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }

        Button(
            modifier = Modifier
                .padding(MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            onClick = { /*TODO*/ }
        ) {
            TitleSmallText(textResId = buttonResId, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Surface {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                ),
                imageResId = R.drawable.ic_read_calendar,
                titleResId = R.string.welcome_screen_hello_december,
                messageResId = R.string.calendar_screen_no_events_message,
                buttonResId = R.string.button_add_permission,
            )
        }
    }
}
