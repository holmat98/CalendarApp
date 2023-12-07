package com.mateuszholik.data.di

import com.mateuszholik.data.mappers.CursorToCalendarsMapper
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
    abstract fun bindsCursorToEventsMapper(
        cursorToEventsMapperImpl: CursorToEventsMapperImpl,
    ): CursorToEventsMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToListOfDaysMapper(
        cursorToListOfDaysMapperImpl: CursorToListOfDaysMapperImpl
    ): CursorToListOfDaysMapper

    @Singleton
    @Binds
    abstract fun bindsCursorToCalendarMapper(
        cursorToCalendarsMapperImpl: CursorToListOfDaysMapperImpl
    ): CursorToCalendarsMapper
}
