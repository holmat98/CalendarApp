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

abstract class PermissionManager(
    private val context: Context,
    private val preferenceAssistant: PreferenceAssistant,
    private val permissions: List<Permission>,
) {

    private val _permissionState: MutableStateFlow<State> = MutableStateFlow(getState())
    val permissionState: StateFlow<State>
        get() = _permissionState.asStateFlow()

    fun arePermissionsGranted(): Boolean =
        permissions.all { it.isGranted() }

    suspend fun handlePermissionsResult(result: Map<String, Boolean>) {
        result.forEach { (permissionName, isGranted) ->
            permissions.find { it.name == permissionName }?.let { permission ->
                val nextState = permission
                    .toPermissionState()
                    .nextPermissionState(isGranted, permission.isOptional)

                preferenceAssistant.write(permissionName, nextState.name)
            }
        }

        _permissionState.emit(getState())
    }

    suspend fun handleBackFromSettings() {
        permissions.forEach {
            val isGranted = it.isGranted()
            val nextState = it
                .toPermissionState()
                .nextPermissionState(isGranted, it.isOptional)

            preferenceAssistant.write(it.name, nextState.name)
        }

        _permissionState.emit(
            if (permissions.areAllPermissionsGranted()) {
                State.PermissionsGranted
            } else {
                State.ShowSettings()
            }
        )
    }

    private fun getState(): State {
        if (permissions.areAllPermissionsGranted()) {
            return State.PermissionsGranted
        }

        val permissionsToShowRationale = permissions
            .filter {
                val permissionState = it.toPermissionState()

                permissionState == PermissionState.SHOW_RATIONALE || permissionState == PermissionState.REMOVED_IN_SETTINGS
            }

        if (permissionsToShowRationale.isNotEmpty()) {
            return State.ShowRationale(permissionsToShowRationale.map { it.name })
        }

        val permissionsToAsk = permissions
            .filter { it.toPermissionState() == PermissionState.NOT_ASKED }

        if (permissionsToAsk.isNotEmpty()) {
            return State.AskForPermissions(permissionsToAsk.map { it.name })
        }

        return State.ShowSettings()
    }

    private fun List<Permission>.areAllPermissionsGranted(): Boolean =
        map {
            val state = it.toPermissionState()
            when {
                Build.VERSION.SDK_INT < it.minSdk -> PermissionState.GRANTED
                state == PermissionState.GRANTED && !it.isGranted() -> {
                    preferenceAssistant.write(it.name, PermissionState.REMOVED_IN_SETTINGS.name)

                    PermissionState.NOT_ASKED
                }
                else -> state
            }
        }
            .all { it == PermissionState.GRANTED }

    private fun Permission.toPermissionState(): PermissionState =
        PermissionState.entries.find {
            it.name == preferenceAssistant.read<String>(name, PermissionState.NOT_ASKED.name)
        } ?: PermissionState.NOT_ASKED

    private fun Permission.isGranted(): Boolean =
        Build.VERSION.SDK_INT < minSdk ||
                ContextCompat.checkSelfPermission(context, name) == PackageManager.PERMISSION_GRANTED

    private fun PermissionState.nextPermissionState(
        isGranted: Boolean,
        isOptional: Boolean,
    ): PermissionState =
        when {
            isGranted || (this == PermissionState.NOT_ASKED && isOptional) -> PermissionState.GRANTED
            this == PermissionState.NOT_ASKED -> PermissionState.SHOW_RATIONALE
            else -> PermissionState.SHOW_SETTINGS
        }


    data class Permission(
        val name: String,
        val isOptional: Boolean,
        val minSdk: Int = Build.VERSION_CODES.BASE,
    )

    sealed class State {
        data class AskForPermissions(val permissions: List<String>) : State()
        data class ShowRationale(val permissions: List<String>) : State()

        class ShowSettings : State()

        data object PermissionsGranted : State()
    }

    private enum class PermissionState {
        NOT_ASKED,
        SHOW_RATIONALE,
        SHOW_SETTINGS,
        REMOVED_IN_SETTINGS,
        GRANTED
    }
}

class CalendarPermissionsManager(
    context: Context,
    preferenceAssistant: PreferenceAssistant,
) : PermissionManager(
    context = context,
    preferenceAssistant = preferenceAssistant,
    permissions = listOf(
        Permission(
            name = Manifest.permission.READ_CALENDAR,
            isOptional = false,
            minSdk = Build.VERSION_CODES.ICE_CREAM_SANDWICH,
        ),
        Permission(
            name = Manifest.permission.WRITE_CALENDAR,
            isOptional = false,
            minSdk = Build.VERSION_CODES.ICE_CREAM_SANDWICH,
        )
    )
)
