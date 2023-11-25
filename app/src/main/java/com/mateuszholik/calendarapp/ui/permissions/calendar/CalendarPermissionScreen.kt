package com.mateuszholik.calendarapp.ui.permissions.calendar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.extensions.shimmerEffect
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
        val paddingValues = PaddingValues(
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
            start = MaterialTheme.spacing.normal,
            end = MaterialTheme.spacing.normal,
        )

        when (uiState) {
            is CalendarPermissionViewModel.CalendarPermissionUiState.AskForReadCalendarPermission -> {
                Content(
                    paddingValues = paddingValues,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.calendar_permission_screen_read_title,
                    messageResId = R.string.calendar_permission_screen_read_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            is CalendarPermissionViewModel.CalendarPermissionUiState.AskForWriteCalendarPermission -> {
                Content(
                    paddingValues = paddingValues,
                    imageResId = R.drawable.ic_write_calendar,
                    titleResId = R.string.calendar_permission_screen_write_title,
                    messageResId = R.string.calendar_permission_screen_write_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            CalendarPermissionViewModel.CalendarPermissionUiState.Loading -> {
                ShimmerContent(paddingValues = paddingValues)
            }
            is CalendarPermissionViewModel.CalendarPermissionUiState.ShowRationaleForReadCalendarPermission -> {
                Content(
                    paddingValues = paddingValues,
                    imageResId = R.drawable.ic_read_calendar,
                    titleResId = R.string.calendar_permission_screen_read_title,
                    messageResId = R.string.calendar_permission_screen_read_rationale_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            is CalendarPermissionViewModel.CalendarPermissionUiState.ShowRationaleForWriteCalendarPermission -> {
                Content(
                    paddingValues = paddingValues,
                    imageResId = R.drawable.ic_write_calendar,
                    titleResId = R.string.calendar_permission_screen_write_title,
                    messageResId = R.string.calendar_permission_screen_write_rationale_message,
                    buttonResId = R.string.button_add_permission,
                )
            }
            CalendarPermissionViewModel.CalendarPermissionUiState.ShowSettings -> {
                Content(
                    paddingValues = paddingValues,
                    imageResId = R.drawable.ic_settings,
                    titleResId = R.string.calendar_permission_screen_settings_title,
                    messageResId = R.string.calendar_permission_screen_settings_message,
                    buttonResId = R.string.button_go_to_settings,
                )
            }
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(MaterialTheme.sizing.extraBig)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.normal)
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.big)
                    .shimmerEffect(),
            )
            Box(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.normal)
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.normal)
                    .shimmerEffect(),
            )
        }

        Box(
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.normal)
                .fillMaxWidth()
                .height(MaterialTheme.sizing.normal)
                .shimmerEffect(),
        )
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
                .padding(vertical = MaterialTheme.spacing.normal)
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
                titleResId = R.string.calendar_permission_screen_read_title,
                messageResId = R.string.calendar_permission_screen_read_message,
                buttonResId = R.string.button_add_permission,
            )
        }
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhoneDarkModePreview() {
    CalendarAppTheme(
        styleType = StyleType.SUMMER,
        darkTheme = true,
    ) {
        Surface {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                ),
                imageResId = R.drawable.ic_read_calendar,
                titleResId = R.string.calendar_permission_screen_read_title,
                messageResId = R.string.calendar_permission_screen_read_message,
                buttonResId = R.string.button_add_permission,
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Surface {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                ),
                imageResId = R.drawable.ic_write_calendar,
                titleResId = R.string.calendar_permission_screen_write_title,
                messageResId = R.string.calendar_permission_screen_write_rationale_message,
                buttonResId = R.string.button_add_permission,
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhoneDarkModePreview() {
    CalendarAppTheme(
        styleType = StyleType.WINTER,
        darkTheme = true,
    ) {
        Surface {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                ),
                imageResId = R.drawable.ic_write_calendar,
                titleResId = R.string.calendar_permission_screen_write_title,
                messageResId = R.string.calendar_permission_screen_write_rationale_message,
                buttonResId = R.string.button_add_permission,
            )
        }
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhoneDarkModePreview2() {
    CalendarAppTheme(
        styleType = StyleType.WINTER,
        darkTheme = true,
    ) {
        Surface {
            ShimmerContent(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                )
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Surface {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                ),
                imageResId = R.drawable.ic_settings,
                titleResId = R.string.calendar_permission_screen_settings_title,
                messageResId = R.string.calendar_permission_screen_settings_message,
                buttonResId = R.string.button_go_to_settings,
            )
        }
    }
}

@BigPhonePreview
@Composable
private fun BigPhoneDarkModePreview() {
    CalendarAppTheme(
        styleType = StyleType.AUTUMN,
        darkTheme = true,
    ) {
        Surface {
            Content(
                paddingValues = PaddingValues(
                    horizontal = MaterialTheme.spacing.normal
                ),
                imageResId = R.drawable.ic_settings,
                titleResId = R.string.calendar_permission_screen_settings_title,
                messageResId = R.string.calendar_permission_screen_settings_message,
                buttonResId = R.string.button_go_to_settings,
            )
        }
    }
}
