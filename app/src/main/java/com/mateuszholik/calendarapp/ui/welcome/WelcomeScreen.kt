package com.mateuszholik.calendarapp.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszholik.calendarapp.ui.theme.CalendarAppTheme
import com.mateuszholik.calendarapp.ui.theme.models.StyleType

@Composable
fun WelcomeScreen(
    goToNextScreen: () -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    Content()
}

@Composable
private fun Content() {

}

@Preview
@Composable
fun Preview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Content()
    }
}
