package com.mateuszholik.domain.di

import com.mateuszholik.domain.usecases.GetCalendarsUseCase
import com.mateuszholik.domain.usecases.GetCalendarsUseCaseImpl
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCase
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCaseImpl
import com.mateuszholik.domain.usecases.GetEditableEventDetailsUseCase
import com.mateuszholik.domain.usecases.GetEditableEventDetailsUseCaseImpl
import com.mateuszholik.domain.usecases.GetEventDetailsUseCase
import com.mateuszholik.domain.usecases.GetEventDetailsUseCaseImpl
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

    @Binds
    abstract fun bindsGetCalendarsUseCase(
        getCalendarUseCaseImpl: GetCalendarsUseCaseImpl,
    ): GetCalendarsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetDaysWithEventsForMonthUseCase(
        getDaysWithEventsForMonthUseCaseImpl: GetDaysWithEventsForMonthUseCaseImpl
    ): GetDaysWithEventsForMonthUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEditableEventDetailsUseCase(
        getEditableEventDetailsUseCaseImpl: GetEditableEventDetailsUseCaseImpl
    ): GetEditableEventDetailsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEventDetailsUseCase(
        getEventDetailsUseCaseImpl: GetEventDetailsUseCaseImpl,
    ): GetEventDetailsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEventsForDayUseCase(
        getEventsForDayUseCaseImpl: GetEventsForDayUseCaseImpl
    ): GetEventsForDayUseCase

    @Binds
    abstract fun bindsUpdateCalendarVisibilityUseCase(
        updateCalendarsUseCaseImpl: UpdateCalendarVisibilityUseCaseImpl
    ): UpdateCalendarVisibilityUseCase
}
