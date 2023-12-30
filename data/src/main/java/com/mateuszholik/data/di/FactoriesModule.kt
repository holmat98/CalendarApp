package com.mateuszholik.data.di

import com.mateuszholik.data.factories.AlertsContentProviderQueryFactory
import com.mateuszholik.data.factories.AlertsContentProviderQueryFactoryImpl
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactory
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactoryImpl
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
    abstract fun bindsAlertsContentProviderQueryFactory(
        alertsContentProviderQueryFactoryImpl: AlertsContentProviderQueryFactoryImpl,
    ): AlertsContentProviderQueryFactory

    @Singleton
    @Binds
    abstract fun bindsAttendeesContentProviderQueryFactory(
        attendeesContentProviderQueryFactoryImpl: AttendeesContentProviderQueryFactoryImpl,
    ): AttendeesContentProviderQueryFactory

    @Singleton
    @Binds
    abstract fun bindsCalendarContentProviderQueryFactory(
        calendarContentProviderQueryFactoryImpl: CalendarContentProviderQueryFactoryImpl,
    ): CalendarContentProviderQueryFactory

    @Singleton
    @Binds
    abstract fun bindsEventsContentProviderQueryFactory(
        eventsContentProviderQueryFactoryImpl: EventsContentProviderQueryFactoryImpl,
    ): EventsContentProviderQueryFactory
}
