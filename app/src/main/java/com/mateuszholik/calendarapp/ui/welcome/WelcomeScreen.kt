package com.mateuszholik.calendarapp.ui.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.observers.ObserveAsEvents
import com.mateuszholik.calendarapp.ui.welcome.models.WelcomeScreenUiEvent.NavigateToNextScreen
import com.mateuszholik.calendarapp.ui.welcome.models.WelcomeScreenUiEvent.NavigateToPermissionsScreen
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.designsystem.ChangeSystemBarColors
import com.mateuszholik.designsystem.models.StyleType
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.DisplayLargeText

@Composable
fun WelcomeScreen(
    goToNextScreen: () -> Unit,
    goToPermissionScreen: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChangeSystemBarColors(navigationBarColor = MaterialTheme.colorScheme.primary)

    ObserveAsEvents(viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            NavigateToNextScreen -> goToNextScreen()
            NavigateToPermissionsScreen -> goToPermissionScreen()
        }
    }

    Content(
        text = state.text,
        image = state.image
    )
}

@Composable
private fun Content(
    @StringRes text: Int,
    @DrawableRes image: Int,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
    ) {
        DisplayLargeText(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.normal),
            textResId = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
        )
        Image(
            modifier = Modifier
                .padding(
                    top = MaterialTheme.spacing.normal,
                    start = MaterialTheme.spacing.normal,
                    end = MaterialTheme.spacing.normal,
                )
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(image),
            contentScale = ContentScale.Inside,
            contentDescription = null
        )
    }
}

@SmallPhonePreview
@Composable
private fun SmallPhonePreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Content(
            text = R.string.welcome_screen_hello_november,
            image = R.drawable.ic_autumn_3
        )
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Content(
            text = R.string.welcome_screen_hello_spring,
            image = R.drawable.ic_spring_1
        )
    }
}

@MediumPhonePreview
@Composable
private fun MediumPhonePreview2() {
    CalendarAppTheme(styleType = StyleType.WINTER) {
        Content(
            text = R.string.welcome_screen_hello_santa,
            image = R.drawable.ic_santa_claus
        )
    }
}

@BigPhonePreview
@Composable
private fun BigPhonePreview() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Content(
            text = R.string.welcome_screen_hello_summer,
            image = R.drawable.ic_summer_3
        )
    }
}
