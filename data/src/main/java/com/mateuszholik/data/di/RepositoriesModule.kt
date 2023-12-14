package com.mateuszholik.data.di

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
    abstract fun bindsEventsRepository(
        eventsRepositoryImpl: EventsRepositoryImpl
    ): EventsRepository

    @Singleton
    @Binds
    abstract fun bindsCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl,
    ): CalendarRepository
}
