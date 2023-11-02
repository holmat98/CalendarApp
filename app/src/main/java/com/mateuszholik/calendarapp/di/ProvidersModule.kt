package com.mateuszholik.calendarapp.di

import com.mateuszholik.calendarapp.ui.theme.provider.StyleProvider
import com.mateuszholik.calendarapp.ui.theme.provider.StyleProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ProvidersModule {

    @Binds
    abstract fun bindsStyleProvider(
        styleProviderImpl: StyleProviderImpl,
    ): StyleProvider
}
