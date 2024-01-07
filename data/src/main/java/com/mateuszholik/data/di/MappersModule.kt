package com.mateuszholik.data.di

import com.mateuszholik.data.mappers.CursorToAlertsMapper
import com.mateuszholik.data.mappers.CursorToAlertsMapperImpl
import com.mateuszholik.data.mappers.CursorToAttendeesMapper
import com.mateuszholik.data.mappers.CursorToAttendeesMapperImpl
import com.mateuszholik.data.mappers.CursorToCalendarIdsMapper
import com.mateuszholik.data.mappers.CursorToCalendarIdsMapperImpl
import com.mateuszholik.data.mappers.CursorToCalendarsMapper
import com.mateuszholik.data.mappers.CursorToCalendarsMapperImpl
import com.mateuszholik.data.mappers.CursorToEventDetailsMapper
import com.mateuszholik.data.mappers.CursorToEventDetailsMapperImpl
import com.mateuszholik.data.mappers.CursorToEventsMapper
import com.mateuszholik.data.mappers.CursorToEventsMapperImpl
import com.mateuszholik.data.mappers.CursorToListOfDaysMapper
import com.mateuszholik.data.mappers.CursorToListOfDaysMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MappersModule {

    @Singleton
    @Binds
    abstract fun bindsCursorToAlertsMapper(
        cursorToAlertsMapperImpl: CursorToAlertsMapperImpl,
    ): CursorToAlertsMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToAttendeesMapper(
        cursorToAttendeesMapperImpl: CursorToAttendeesMapperImpl,
    ): CursorToAttendeesMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToCalendarIdsMapper(
        cursorToCalendarIdsMapperImpl: CursorToCalendarIdsMapperImpl,
    ): CursorToCalendarIdsMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToCalendarsMapper(
        cursorToCalendarsMapperImpl: CursorToCalendarsMapperImpl,
    ): CursorToCalendarsMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToEventDetailsMapper(
        cursorToEventDetailsMapperImpl: CursorToEventDetailsMapperImpl,
    ): CursorToEventDetailsMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToEventsMapper(
        cursorToEventsMapperImpl: CursorToEventsMapperImpl,
    ): CursorToEventsMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToListOfDaysMapper(
        cursorToListOfDaysMapperImpl: CursorToListOfDaysMapperImpl,
    ): CursorToListOfDaysMapper
}
