package com.mateuszholik.calendarapp.ui.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.designsystem.previews.BigPhonePreview
import com.mateuszholik.designsystem.previews.MediumPhonePreview
import com.mateuszholik.designsystem.previews.SmallPhonePreview
import com.mateuszholik.designsystem.textSizing
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.NextScreen
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.WelcomeInfo
import com.mateuszholik.designsystem.CalendarAppTheme

@Composable
fun WelcomeScreen(
    goToNextScreen: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is WelcomeInfo -> {
            val info = state as WelcomeInfo
            Content(
                text = info.text,
                image = info.image
            )
        }
        is NextScreen -> LaunchedEffect(Unit) {
            goToNextScreen()
        }
    }
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
        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = stringResource(id = text),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = MaterialTheme.textSizing.bigHeader,
            fontWeight = FontWeight.Bold,
            lineHeight = MaterialTheme.textSizing.bigHeader
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            painter = painterResource(image),
            contentScale = ContentScale.Inside,
            contentDescription = null
        )
    }
}

@SmallPhonePreview
@Composable
fun Preview() {
    CalendarAppTheme(styleType = com.mateuszholik.designsystem.models.StyleType.AUTUMN) {
        Content(
            text = R.string.welcome_screen_hello_november,
            image = R.drawable.ic_autumn_3
        )
    }
}

@MediumPhonePreview
@Composable
fun Preview2() {
    CalendarAppTheme(styleType = com.mateuszholik.designsystem.models.StyleType.SPRING) {
        Content(
            text = R.string.welcome_screen_hello_spring,
            image = R.drawable.ic_spring_1
        )
    }
}

@BigPhonePreview
@Composable
fun Preview3() {
    CalendarAppTheme(styleType = com.mateuszholik.designsystem.models.StyleType.SUMMER) {
        Content(
            text = R.string.welcome_screen_hello_summer,
            image = R.drawable.ic_summer_3
        )
    }
}
