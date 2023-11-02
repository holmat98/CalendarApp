package com.mateuszholik.calendarapp.ui.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.ui.theme.CalendarAppTheme
import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.WelcomeInfo
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.NextScreen
import com.mateuszholik.calendarapp.R

@Composable
fun WelcomeScreen(
    goToNextScreen: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is WelcomeInfo -> {
            val info = state as WelcomeInfo
            Content(info.text, info.image)
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
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 60.sp
        )
        Image(
            modifier = Modifier.padding(32.dp),
            painter = painterResource(image),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun Preview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Content(
            text = R.string.welcome_screen_hello_october,
            image = R.drawable.ic_autumn_2
        )
    }
}
