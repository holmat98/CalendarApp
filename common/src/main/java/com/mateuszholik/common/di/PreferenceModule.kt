package com.mateuszholik.common.di

import com.mateuszholik.common.PermissionsPreferenceAssistant
import com.mateuszholik.common.PreferenceAssistant
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Permissions

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PreferenceModule {

    @Permissions
    @Binds
    abstract fun bindsPermissionsPreferenceAssistant(
        permissionsPreferenceAssistant: PermissionsPreferenceAssistant
    ): PreferenceAssistant
}
