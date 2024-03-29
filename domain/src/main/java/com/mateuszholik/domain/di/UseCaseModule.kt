package com.mateuszholik.domain.di

import com.mateuszholik.domain.usecases.CreateEventUseCase
import com.mateuszholik.domain.usecases.CreateEventUseCaseImpl
import com.mateuszholik.domain.usecases.DeleteEventUseCase
import com.mateuszholik.domain.usecases.DeleteEventUseCaseImpl
import com.mateuszholik.domain.usecases.GetCalendarUseCase
import com.mateuszholik.domain.usecases.GetCalendarUseCaseImpl
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
import com.mateuszholik.domain.usecases.UpdateEventDetailsUseCase
import com.mateuszholik.domain.usecases.UpdateEventDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @Binds
    abstract fun bindsCreateEventUseCase(
        createEventUseCaseImpl: CreateEventUseCaseImpl,
    ): CreateEventUseCase

    @Binds
    abstract fun bindsDeleteUseCase(
        deleteEventUseCaseImpl: DeleteEventUseCaseImpl,
    ): DeleteEventUseCase

    @Binds
    abstract fun bindsGetCalendarsUseCase(
        getCalendarsUseCaseImpl: GetCalendarsUseCaseImpl,
    ): GetCalendarsUseCase

    @Binds
    abstract fun bindsGetCalendarUseCase(
        getCalendarUseCaseImpl: GetCalendarUseCaseImpl,
    ): GetCalendarUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetDaysWithEventsForMonthUseCase(
        getDaysWithEventsForMonthUseCaseImpl: GetDaysWithEventsForMonthUseCaseImpl,
    ): GetDaysWithEventsForMonthUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEditableEventDetailsUseCase(
        getEditableEventDetailsUseCaseImpl: GetEditableEventDetailsUseCaseImpl,
    ): GetEditableEventDetailsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEventDetailsUseCase(
        getEventDetailsUseCaseImpl: GetEventDetailsUseCaseImpl,
    ): GetEventDetailsUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindsGetEventsForDayUseCase(
        getEventsForDayUseCaseImpl: GetEventsForDayUseCaseImpl,
    ): GetEventsForDayUseCase

    @Binds
    abstract fun bindsUpdateCalendarVisibilityUseCase(
        updateCalendarsUseCaseImpl: UpdateCalendarVisibilityUseCaseImpl,
    ): UpdateCalendarVisibilityUseCase

    @Binds
    abstract fun bindsUpdateEventDetailsUseCase(
        updateEventDetailsUseCaseImpl: UpdateEventDetailsUseCaseImpl,
    ): UpdateEventDetailsUseCase
}
