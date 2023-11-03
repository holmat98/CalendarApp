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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.model.WindowSizeInfo
import com.mateuszholik.calendarapp.ui.model.WindowType
import com.mateuszholik.calendarapp.ui.model.rememberWindowSize
import com.mateuszholik.calendarapp.ui.previews.BigPhonePreview
import com.mateuszholik.calendarapp.ui.previews.MediumPhonePreview
import com.mateuszholik.calendarapp.ui.previews.SmallPhonePreview
import com.mateuszholik.calendarapp.ui.theme.CalendarAppTheme
import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.NextScreen
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.WelcomeInfo

@Composable
fun WelcomeScreen(
    windowSizeInfo: WindowSizeInfo,
    goToNextScreen: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is WelcomeInfo -> {
            val info = state as WelcomeInfo
            Content(
                windowSizeInfo = windowSizeInfo,
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
    windowSizeInfo: WindowSizeInfo,
    @StringRes text: Int,
    @DrawableRes image: Int,
) {
    val textSize = remember {
        when (windowSizeInfo.widthInfo) {
            WindowType.SMALL -> 50.sp
            WindowType.MEDIUM -> 65.sp
            WindowType.BIG -> 80.sp
        }
    }

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
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            lineHeight = textSize
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
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Content(
            windowSizeInfo = rememberWindowSize(),
            text = R.string.welcome_screen_hello_november,
            image = R.drawable.ic_autumn_3
        )
    }
}

@MediumPhonePreview
@Composable
fun Preview2() {
    CalendarAppTheme(styleType = StyleType.SPRING) {
        Content(
            windowSizeInfo = rememberWindowSize(),
            text = R.string.welcome_screen_hello_spring,
            image = R.drawable.ic_spring_1
        )
    }
}

@BigPhonePreview
@Composable
fun Preview3() {
    CalendarAppTheme(styleType = StyleType.SUMMER) {
        Content(
            windowSizeInfo = rememberWindowSize(),
            text = R.string.welcome_screen_hello_summer,
            image = R.drawable.ic_summer_3
        )
    }
}
