package com.mateuszholik.calendarapp.ui.permissions.calendar

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUiEvent
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUiState
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionViewModel.CalendarPermissionUserAction
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.sizing
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.animations.CommonAnimation
import com.mateuszholik.uicomponents.buttons.CommonButton
import com.mateuszholik.uicomponents.extensions.shimmerEffect
import com.mateuszholik.uicomponents.text.HeadlineMediumText
import com.mateuszholik.uicomponents.text.TitleMediumText

@Composable
fun CalendarPermissionScreen(
    onPermissionGranted: () -> Unit,
    viewModel: CalendarPermissionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        viewModel.performUserAction(
            CalendarPermissionUserAction.OnCalendarPermissionResultUserAction(
                results = results
            )
        )
    }
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) {
        viewModel.performUserAction(
            CalendarPermissionUserAction.OnReturnBackFromSettingsUserAction
        )
    }

    ObserveAsEvents(flow = viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            CalendarPermissionUiEvent.AllPermissionsGranted -> onPermissionGranted()
        }
    }

    Scaffold(containerColor = MaterialTheme.colorScheme.surface) {
        val paddingValues = PaddingValues(
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
            start = MaterialTheme.spacing.normal,
            end = MaterialTheme.spacing.normal,
        )

        when (uiState) {
            is CalendarPermissionUiState.Loading -> {
                ShimmerContent(paddingValues = paddingValues)
            }
            is CalendarPermissionUiState.AskForCalendarPermissions -> {
                val permissions =
                    (uiState as CalendarPermissionUiState.AskForCalendarPermissions).permissions
                Content(
                    paddingValues = paddingValues,
                    animationResId = R.raw.calendar_permission_anim,
                    titleResId = R.string.calendar_permission_screen_title,
                    messageResId = R.string.calendar_permission_screen_message,
                    buttonResId = R.string.button_add_permission,
                    onButtonClicked = {
                        permissionLauncher.launch(permissions.toTypedArray())
                    },
                )
            }
            is CalendarPermissionUiState.ShowRationaleForCalendarPermissions -> {
                val permissions =
                    (uiState as CalendarPermissionUiState.ShowRationaleForCalendarPermissions).permissions
                Content(
                    paddingValues = paddingValues,
                    animationResId = R.raw.calendar_permission_anim,
                    titleResId = R.string.calendar_permission_screen_title,
                    messageResId = R.string.calendar_permission_screen_rationale_message,
                    buttonResId = R.string.button_add_permission,
                    onButtonClicked = {
                        permissionLauncher.launch(permissions.toTypedArray())
                    }
                )
            }
            is CalendarPermissionUiState.ShowSettings -> {
                val context = LocalContext.current
                Content(
                    paddingValues = paddingValues,
                    animationResId = R.raw.calendar_permission_settings_anim,
                    titleResId = R.string.calendar_permission_screen_settings_title,
                    messageResId = R.string.calendar_permission_screen_settings_message,
                    buttonResId = R.string.button_go_to_settings,
                    onButtonClicked = {
                        settingsLauncher.launch(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }
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
    @RawRes animationResId: Int,
    @StringRes titleResId: Int,
    @StringRes messageResId: Int,
    @StringRes buttonResId: Int,
    onButtonClicked: () -> Unit,
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
            CommonAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.sizing.extraBig),
                animationResId = animationResId,
                isLooped = true,
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

        CommonButton(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.normal)
                .fillMaxWidth(),
            textResId = buttonResId,
            onClick = onButtonClicked,
        )
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
                animationResId = R.raw.calendar_permission_anim,
                titleResId = R.string.calendar_permission_screen_title,
                messageResId = R.string.calendar_permission_screen_message,
                buttonResId = R.string.button_add_permission,
                onButtonClicked = {},
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
                animationResId = R.raw.calendar_permission_anim,
                titleResId = R.string.calendar_permission_screen_title,
                messageResId = R.string.calendar_permission_screen_message,
                buttonResId = R.string.button_add_permission,
                onButtonClicked = {},
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
                animationResId = R.raw.calendar_permission_anim,
                titleResId = R.string.calendar_permission_screen_title,
                messageResId = R.string.calendar_permission_screen_rationale_message,
                buttonResId = R.string.button_add_permission,
                onButtonClicked = {},
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
                animationResId = R.raw.calendar_permission_anim,
                titleResId = R.string.calendar_permission_screen_title,
                messageResId = R.string.calendar_permission_screen_rationale_message,
                buttonResId = R.string.button_add_permission,
                onButtonClicked = {},
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
                animationResId = R.raw.calendar_permission_settings_anim,
                titleResId = R.string.calendar_permission_screen_settings_title,
                messageResId = R.string.calendar_permission_screen_settings_message,
                buttonResId = R.string.button_go_to_settings,
                onButtonClicked = {},
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
                animationResId = R.raw.calendar_permission_settings_anim,
                titleResId = R.string.calendar_permission_screen_settings_title,
                messageResId = R.string.calendar_permission_screen_settings_message,
                buttonResId = R.string.button_go_to_settings,
                onButtonClicked = {},
            )
        }
    }
}
