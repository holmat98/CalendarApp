package com.mateuszholik.common.di

import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.common.provider.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProviderModule {

    @Singleton
    @Binds
    abstract fun bindsDispatcherProvider(
        dispatcherProviderImpl: DispatcherProviderImpl,
    ): DispatcherProvider
}
