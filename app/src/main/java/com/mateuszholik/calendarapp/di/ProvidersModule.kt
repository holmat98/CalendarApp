package com.mateuszholik.calendarapp.di

import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.calendarapp.provider.CurrentDateProviderImpl
import com.mateuszholik.calendarapp.provider.DispatcherProvider
import com.mateuszholik.calendarapp.provider.DispatcherProviderImpl
import com.mateuszholik.calendarapp.ui.provider.StyleProvider
import com.mateuszholik.calendarapp.ui.provider.StyleProviderImpl
import com.mateuszholik.calendarapp.ui.welcome.provider.WelcomeScreenInfoProvider
import com.mateuszholik.calendarapp.ui.welcome.provider.WelcomeScreenInfoProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ProvidersModule {

    @Binds
    abstract fun bindsStyleProvider(
        styleProviderImpl: StyleProviderImpl,
    ): StyleProvider

    @Binds
    abstract fun bindsWelcomeScreenInfoProvider(
        welcomeScreenInfoProviderImpl: WelcomeScreenInfoProviderImpl,
    ): WelcomeScreenInfoProvider
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SingletonsProvidersModule {

    @Binds
    abstract fun bindsCurrentDateProvider(
        currentDateProviderImpl: CurrentDateProviderImpl,
    ): CurrentDateProvider

    @Binds
    abstract fun bindsDispatcherProvider(
        dispatcherProviderImpl: DispatcherProviderImpl
    ): DispatcherProvider
}
