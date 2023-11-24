package com.mateuszholik.calendarapp.ui.permissions.calendar

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalendarPermissionScreen(
    onPermissionGranted: () -> Unit,
    viewModel: CalendarPermissionViewModel = hiltViewModel(),
) {

}
