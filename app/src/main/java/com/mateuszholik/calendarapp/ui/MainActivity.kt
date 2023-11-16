package com.mateuszholik.calendarapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mateuszholik.calendarapp.ui.navigation.MainNavigation
import com.mateuszholik.calendarapp.ui.navigation.MainNavigation.mainNavigationGraph
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
            val navController = rememberNavController()

            CalendarAppTheme(styleType = styleType) {
                NavHost(
                    navController = navController,
                    startDestination = MainNavigation.ROOT
                ) {
                    mainNavigationGraph(navController)
                }
            }
        }
    }
}
