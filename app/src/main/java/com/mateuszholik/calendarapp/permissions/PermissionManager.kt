package com.mateuszholik.calendarapp.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.mateuszholik.common.PreferenceAssistant
import com.mateuszholik.common.read
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

abstract class PermissionManager(
    private val context: Context,
    private val preferenceAssistant: PreferenceAssistant,
) {

    protected abstract val permission: Permission

    private val _permissionState: MutableStateFlow<State> = MutableStateFlow(checkPermissionState())

    val permissionName: String
        get() = permission.name

    val permissionState: StateFlow<State>
        get() = _permissionState.asStateFlow()

    fun isPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(context, permission.name) == PackageManager.PERMISSION_GRANTED

    fun updatePermissionState(isGranted: Boolean) {
        _permissionState.update { currentState ->
            val newState = when {
                isGranted || isPermissionGranted() -> State.GRANTED
                currentState == State.NOT_ASKED -> State.SHOW_RATIONALE
                else -> State.SHOW_SETTINGS
            }

            preferenceAssistant.write(permission.name, newState.name)

            newState
        }
    }

    private fun checkPermissionState(): State {
        val state = State.valueOf(preferenceAssistant.read(permission.name, "NOT_ASKED"))

        return when {
            Build.VERSION.SDK_INT < permission.minSdk -> State.GRANTED
            state == State.GRANTED && !isPermissionGranted() -> {
                preferenceAssistant.write(permission.name, State.NOT_ASKED.name)

                State.NOT_ASKED
            }
            else -> state
        }
    }


    data class Permission(
        val name: String,
        val isRequired: Boolean,
        val minSdk: Int = Build.VERSION_CODES.BASE,
    )

    enum class State {
        NOT_ASKED,
        SHOW_RATIONALE,
        SHOW_SETTINGS,
        GRANTED,
    }
}

class ReadCalendarPermissionManager @Inject constructor(
    context: Context,
    preferenceAssistant: PreferenceAssistant,
) : PermissionManager(context, preferenceAssistant) {

    override val permission: Permission = Permission(
        name = Manifest.permission.READ_CALENDAR,
        isRequired = true
    )
}

class WriteCalendarPermissionManager @Inject constructor(
    context: Context,
    preferenceAssistant: PreferenceAssistant,
) : PermissionManager(context, preferenceAssistant) {

    override val permission: Permission = Permission(
        name = Manifest.permission.WRITE_CALENDAR,
        isRequired = true
    )
}
