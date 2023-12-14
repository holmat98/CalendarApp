package com.mateuszholik.domain.di

import com.mateuszholik.domain.usecases.GetCalendarsUseCase
import com.mateuszholik.domain.usecases.GetCalendarsUseCaseImpl
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCase
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCaseImpl
import com.mateuszholik.domain.usecases.GetEventsForDayUseCase
import com.mateuszholik.domain.usecases.GetEventsForDayUseCaseImpl
import com.mateuszholik.domain.usecases.UpdateCalendarVisibilityUseCase
import com.mateuszholik.domain.usecases.UpdateCalendarVisibilityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @ViewModelScoped
    @Binds
    abstract fun bindsGetDaysWithEventsForMonthUseCase(
        getDaysWithEventsForMonthUseCaseImpl: GetDaysWithEventsForMonthUseCaseImpl
    ): GetDaysWithEventsForMonthUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEventsForDayUseCase(
        getEventsForDayUseCaseImpl: GetEventsForDayUseCaseImpl
    ): GetEventsForDayUseCase

    @Binds
    abstract fun bindsGetCalendarsUseCase(
        getCalendarUseCaseImpl: GetCalendarsUseCaseImpl,
    ): GetCalendarsUseCase

    @Binds
    abstract fun bindsUpdateCalendarVisibilityUseCase(
        updateCalendarsUseCaseImpl: UpdateCalendarVisibilityUseCaseImpl
    ): UpdateCalendarVisibilityUseCase
}
