package com.mateuszholik.data.di

import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactoryImpl
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory
import com.mateuszholik.data.factories.EventsContentProviderQueryFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FactoriesModule {

    @Singleton
    @Binds
    abstract fun bindsEventsContentProviderQueryFactory(
        eventsContentProviderQueryFactoryImpl: EventsContentProviderQueryFactoryImpl,
    ): EventsContentProviderQueryFactory

    @Singleton
    @Binds
    abstract fun bindsCalendarContentProviderQueryFactory(
        calendarContentProviderQueryFactoryImpl: CalendarContentProviderQueryFactoryImpl,
    ): CalendarContentProviderQueryFactory
}
