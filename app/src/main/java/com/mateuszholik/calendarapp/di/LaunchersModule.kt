package com.mateuszholik.calendarapp.di

import com.mateuszholik.calendarapp.launchers.WebsiteLauncher
import com.mateuszholik.calendarapp.launchers.WebsiteLauncherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LaunchersModule {

    @Binds
    abstract fun bindsWebsiteLauncher(
        websiteLauncherImpl: WebsiteLauncherImpl
    ): WebsiteLauncher
}
