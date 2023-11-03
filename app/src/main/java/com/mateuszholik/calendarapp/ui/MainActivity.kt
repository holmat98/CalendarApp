package com.mateuszholik.calendarapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszholik.calendarapp.ui.model.rememberWindowSize
import com.mateuszholik.calendarapp.ui.theme.CalendarAppTheme
import com.mateuszholik.calendarapp.ui.theme.models.StyleType
import com.mateuszholik.calendarapp.ui.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val styleType = remember { viewModel.currentStyle }
            val windowSizeInfo = rememberWindowSize()

            CalendarAppTheme(styleType = styleType) {
                WelcomeScreen(
                    windowSizeInfo = windowSizeInfo,
                    goToNextScreen = {}
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalendarAppTheme(styleType = StyleType.AUTUMN) {
        Greeting("Android")
    }
}
