package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToCalendarIdsMapper
import com.mateuszholik.data.mappers.CursorToEventDetailsMapper
import com.mateuszholik.data.mappers.CursorToEventsMapper
import com.mateuszholik.data.mappers.CursorToListOfDaysMapper
import com.mateuszholik.data.repositories.models.Event
import com.mateuszholik.data.repositories.models.EventDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

interface EventsRepository {

    suspend fun getEventsForDay(day: LocalDate): List<Event>

    suspend fun getEventDetails(id: Long): EventDetails?

    suspend fun getDaysWithEventsForMonth(yearMonth: YearMonth): List<LocalDate>
}

internal class EventsRepositoryImpl @Inject constructor(
    private val eventsContentProviderQueryFactory: EventsContentProviderQueryFactory,
    private val calendarContentProviderQueryFactory: CalendarContentProviderQueryFactory,
    private val cursorToEventsMapper: CursorToEventsMapper,
    private val cursorToListOfDaysMapper: CursorToListOfDaysMapper,
    private val cursorToCalendarIdsMapper: CursorToCalendarIdsMapper,
    private val cursorToEventDetailsMapper: CursorToEventDetailsMapper,
    private val dispatcherProvider: DispatcherProvider,
    @ApplicationContext private val context: Context,
) : EventsRepository {

    private val contentResolver by lazy { context.contentResolver }

    override suspend fun getEventsForDay(day: LocalDate): List<Event> {
        val calendarIds = getSelectedCalendarIds()
        val queryData = eventsContentProviderQueryFactory.createForEventsFromDay(
            day = day,
            calendarIds = calendarIds
        )

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val events = cursor
            ?.let { cursorToEventsMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return events
    }

    override suspend fun getEventDetails(id: Long): EventDetails? {
        val queryData = eventsContentProviderQueryFactory.createForEvent(id)

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val event = cursor?.let { cursorToEventDetailsMapper.map(it) }

        cursor?.close()

        return event
    }

    override suspend fun getDaysWithEventsForMonth(yearMonth: YearMonth): List<LocalDate> {
        val queryData = eventsContentProviderQueryFactory.createForEventsFromMonth(
            yearMonth = yearMonth,
            calendarIds = getSelectedCalendarIds()
        )

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val days = cursor
            ?.let { cursorToListOfDaysMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return days
    }

    private suspend fun getSelectedCalendarIds(): List<Long> {
        val queryData = calendarContentProviderQueryFactory.createForSelectedCalendarsIds()

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val ids = cursor
            ?.let { cursorToCalendarIdsMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return ids
    }
}
