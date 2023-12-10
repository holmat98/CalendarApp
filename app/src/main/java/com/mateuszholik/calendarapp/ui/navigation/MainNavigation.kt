package com.mateuszholik.calendarapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mateuszholik.calendarapp.ui.calendar.CalendarScreen
import com.mateuszholik.calendarapp.ui.calendarprofiles.CalendarProfilesScreen
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionScreen
import com.mateuszholik.calendarapp.ui.welcome.WelcomeScreen

object MainNavigation {

    const val ROOT = "Main"
    private const val WELCOME_SCREEN = "$ROOT/WELCOME_SCREEN"
    private const val CALENDAR_PERMISSIONS_SCREEN = "$ROOT/CALENDAR_PERMISSIONS_SCREEN"
    private const val CALENDAR_SCREEN = "$ROOT/CALENDAR_SCREEN"
    private const val CALENDAR_PROFILES_SCREEN = "$ROOT/CALENDAR_PROFILES_SCREEN"

    fun NavGraphBuilder.mainNavigationGraph(navController: NavController) {
        navigation(startDestination = WELCOME_SCREEN, route = ROOT) {
            welcomeScreen(navController)
            calendarPermissionsScreen(navController)
            calendarScreen(navController)
            calendarProfilesScreen(navController)
        }
    }

    private fun NavGraphBuilder.welcomeScreen(navController: NavController) {
        composable(WELCOME_SCREEN) {
            WelcomeScreen(
                goToNextScreen = { navController.navigateToCalendarScreen() },
                goToPermissionScreen = { navController.navigateToCalendarPermissionsScreen() },
            )
        }
    }

    private fun NavGraphBuilder.calendarScreen(navController: NavController) {
        composable(CALENDAR_SCREEN) {
            CalendarScreen(
                onAddEventClicked = {},
                onEventClicked = {},
                onProfileClicked = { navController.navigateToCalendarProfileScreen() }
            )
        }
    }

    private fun NavGraphBuilder.calendarProfilesScreen(navController: NavController) {
        composable(CALENDAR_PROFILES_SCREEN) {
            CalendarProfilesScreen(onBackPressed = { navController.navigateUp() })
        }
    }

    private fun NavGraphBuilder.calendarPermissionsScreen(navController: NavController) {
        composable(CALENDAR_PERMISSIONS_SCREEN) {
            CalendarPermissionScreen(
                onPermissionGranted = { navController.navigateToCalendarScreen() }
            )
        }
    }

    private fun NavController.navigateToCalendarPermissionsScreen() =
        navigate(CALENDAR_PERMISSIONS_SCREEN) {
            popUpTo(ROOT) { inclusive = true }
        }

    private fun NavController.navigateToCalendarScreen() =
        navigate(CALENDAR_SCREEN) {
            popUpTo(ROOT) { inclusive = true }
        }

    private fun NavController.navigateToCalendarProfileScreen() =
        navigate(CALENDAR_PROFILES_SCREEN)
}
