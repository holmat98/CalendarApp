package com.mateuszholik.common.di

import android.content.Context
import com.mateuszholik.common.PreferenceAssistant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AssistantModule {

    @Singleton
    @Provides
    fun providesPreferenceAssistant(
        @ApplicationContext context: Context,
    ): PreferenceAssistant = PreferenceAssistant(context)
}
