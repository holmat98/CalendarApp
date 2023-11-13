package com.mateuszholik.calendarapp.ui.observers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.mateuszholik.calendarapp.ui.base.UiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : UiEvent> ObserveAsEvents(
    flow: Flow<T>,
    onEvent: (event: T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(onEvent)
        }
    }
}
