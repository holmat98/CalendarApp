package com.mateuszholik.calendarapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import com.mateuszholik.designsystem.CalendarAppTheme
import com.mateuszholik.calendarapp.ui.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val styleType = remember { viewModel.currentStyle }

            CalendarAppTheme(styleType = styleType) {
                WelcomeScreen(goToNextScreen = {})
            }
        }
    }
}
