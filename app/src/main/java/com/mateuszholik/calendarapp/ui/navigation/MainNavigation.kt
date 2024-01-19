package com.mateuszholik.calendarapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mateuszholik.calendarapp.ui.calendar.CalendarScreen
import com.mateuszholik.calendarapp.ui.editevent.EditEventScreen
import com.mateuszholik.calendarapp.ui.eventdetails.EventDetailsScreen
import com.mateuszholik.calendarapp.ui.selectcalendars.CalendarsSelectionScreen
import com.mateuszholik.calendarapp.ui.permissions.calendar.CalendarPermissionScreen
import com.mateuszholik.calendarapp.ui.welcome.WelcomeScreen

object MainNavigation {

    const val ROOT = "Main"
    private const val WELCOME_SCREEN = "$ROOT/WELCOME_SCREEN"
    private const val CALENDAR_PERMISSIONS_SCREEN = "$ROOT/CALENDAR_PERMISSIONS_SCREEN"
    private const val CALENDAR_SCREEN = "$ROOT/CALENDAR_SCREEN"
    private const val CALENDAR_PROFILES_SCREEN = "$ROOT/CALENDAR_PROFILES_SCREEN"
    private const val EVENT_DETAILS_SCREEN = "$ROOT/EVENT_DETAILS_SCREEN"
    private const val EDIT_EVENT_SCREEN = "$ROOT/EDIT_EVENT_SCREEN"

    internal const val EVENT_ID_ARGUMENT = "EVENT_ID_KEY"

    fun NavGraphBuilder.mainNavigationGraph(navController: NavController) {
        navigation(startDestination = WELCOME_SCREEN, route = ROOT) {
            welcomeScreen(navController)
            calendarPermissionsScreen(navController)
            calendarScreen(navController)
            calendarsSelectionScreen(navController)
            eventDetailsScreen(navController)
            editEventScreen(navController)
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
                onEventClicked = { navController.navigateToEventDetails(it) },
                onProfileClicked = { navController.navigateToCalendarsSelectionScreen() }
            )
        }
    }

    private fun NavGraphBuilder.calendarsSelectionScreen(navController: NavController) {
        composable(CALENDAR_PROFILES_SCREEN) {
            CalendarsSelectionScreen(onBackPressed = { navController.navigateUp() })
        }
    }

    private fun NavGraphBuilder.calendarPermissionsScreen(navController: NavController) {
        composable(CALENDAR_PERMISSIONS_SCREEN) {
            CalendarPermissionScreen(
                onPermissionGranted = { navController.navigateToCalendarScreen() }
            )
        }
    }

    private fun NavGraphBuilder.eventDetailsScreen(navController: NavController) {
        composable(
            route = "$EVENT_DETAILS_SCREEN/${EVENT_ID_ARGUMENT}={$EVENT_ID_ARGUMENT}",
            arguments = listOf(
                navArgument(EVENT_ID_ARGUMENT) { type = NavType.LongType }
            )
        ) {
            EventDetailsScreen(
                onBackPressed = { navController.navigateUp() },
                onEditPressed = { navController.navigateToEditEventScreen(it) }
            )
        }
    }

    private fun NavGraphBuilder.editEventScreen(navController: NavController) {
        composable(
            route = "$EDIT_EVENT_SCREEN/${EVENT_ID_ARGUMENT}={$EVENT_ID_ARGUMENT}",
            arguments = listOf(
                navArgument(EVENT_ID_ARGUMENT) { type = NavType.LongType }
            )
        ) {
            EditEventScreen(onBackPressed = { navController.navigateUp() })
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

    private fun NavController.navigateToCalendarsSelectionScreen() =
        navigate(CALENDAR_PROFILES_SCREEN)

    private fun NavController.navigateToEventDetails(eventId: Long) =
        navigate("$EVENT_DETAILS_SCREEN/$EVENT_ID_ARGUMENT=$eventId")

    private fun NavController.navigateToEditEventScreen(eventId: Long) =
        navigate("$EDIT_EVENT_SCREEN/$EVENT_ID_ARGUMENT=$eventId")
}
