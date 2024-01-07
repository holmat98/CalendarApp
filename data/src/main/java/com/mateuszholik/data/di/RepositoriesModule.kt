package com.mateuszholik.data.di

import com.mateuszholik.data.repositories.AlertRepository
import com.mateuszholik.data.repositories.AlertRepositoryImpl
import com.mateuszholik.data.repositories.AttendeesRepository
import com.mateuszholik.data.repositories.AttendeesRepositoryImpl
import com.mateuszholik.data.repositories.CalendarRepository
import com.mateuszholik.data.repositories.CalendarRepositoryImpl
import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.data.repositories.EventsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoriesModule {

    @Singleton
    @Binds
    abstract fun bindsAlertRepository(
        alertRepositoryImpl: AlertRepositoryImpl,
    ): AlertRepository

    @Singleton
    @Binds
    abstract fun bindsAttendeesRepository(
        attendeesRepositoryImpl: AttendeesRepositoryImpl,
    ): AttendeesRepository

    @Singleton
    @Binds
    abstract fun bindsCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl,
    ): CalendarRepository

    @Singleton
    @Binds
    abstract fun bindsEventsRepository(
        eventsRepositoryImpl: EventsRepositoryImpl,
    ): EventsRepository
}
