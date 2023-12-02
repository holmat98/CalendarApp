package com.mateuszholik.calendarapp.di

import android.content.Context
import com.mateuszholik.calendarapp.permissions.CalendarPermissionsManager
import com.mateuszholik.common.PreferenceAssistant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
internal object ManagersModule {

    @Provides
    fun providesReadCalendarPermissionManager(
        @ApplicationContext context: Context,
        preferenceAssistant: PreferenceAssistant,
    ): CalendarPermissionsManager =
        CalendarPermissionsManager(context, preferenceAssistant)
}
