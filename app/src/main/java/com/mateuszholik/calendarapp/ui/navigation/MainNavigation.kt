package com.mateuszholik.calendarapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mateuszholik.calendarapp.ui.calendar.CalendarScreen
import com.mateuszholik.calendarapp.ui.welcome.WelcomeScreen

object MainNavigation {

    const val ROOT = "Main"
    private const val WELCOME_SCREEN = "$ROOT/WELCOME_SCREEN"
    private const val CALENDAR_SCREEN = "$ROOT/CALENDAR_SCREEN"

    fun NavGraphBuilder.mainNavigationGraph(navController: NavController) {
        navigation(startDestination = WELCOME_SCREEN, route = ROOT) {
            welcomeScreen(navController)
            calendarScreen()
        }
    }

    private fun NavGraphBuilder.welcomeScreen(navController: NavController) {
        composable(WELCOME_SCREEN) {
            WelcomeScreen(goToNextScreen = { navController.navigateToCalendarScreen() })
        }
    }

    private fun NavGraphBuilder.calendarScreen() {
        composable(CALENDAR_SCREEN) {
            CalendarScreen(onEventClicked = {})
        }
    }

    private fun NavController.navigateToCalendarScreen() =
        navigate(CALENDAR_SCREEN) {
            popUpTo(ROOT) { inclusive = true }
        }
}
