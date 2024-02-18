package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.data.factories.AlertsContentProviderQueryFactory
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToCalendarIdsMapper
import com.mateuszholik.data.mappers.CursorToEventDetailsMapper
import com.mateuszholik.data.mappers.CursorToEventsMapper
import com.mateuszholik.data.mappers.CursorToListOfDaysMapper
import com.mateuszholik.data.repositories.models.Event
import com.mateuszholik.data.repositories.models.EventDetails
import com.mateuszholik.data.repositories.models.NewEvent
import com.mateuszholik.data.repositories.models.UpdatedEventDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

interface EventsRepository {

    suspend fun getEventsForDay(day: LocalDate): List<Event>

    suspend fun getEventDetails(id: Long): EventDetails?

    suspend fun getDaysWithEventsForMonth(yearMonth: YearMonth): List<LocalDate>

    suspend fun updateEventDetails(updatedEventDetails: UpdatedEventDetails)

    suspend fun deleteEvent(eventId: Long)

    suspend fun createEvent(newEvent: NewEvent)
}

internal class EventsRepositoryImpl @Inject constructor(
    private val eventsContentProviderQueryFactory: EventsContentProviderQueryFactory,
    private val calendarContentProviderQueryFactory: CalendarContentProviderQueryFactory,
    private val alertsContentProviderQueryFactory: AlertsContentProviderQueryFactory,
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

    override suspend fun updateEventDetails(updatedEventDetails: UpdatedEventDetails) {
        withContext(dispatcherProvider.io()) {
            val updateData = eventsContentProviderQueryFactory.createForUpdateEvent(
                updatedEventDetails = updatedEventDetails
            )

            contentResolver.update(updateData.uri, updateData.values, null, null)
        }
    }

    override suspend fun deleteEvent(eventId: Long) {
        withContext(dispatcherProvider.io()) {
            val deleteData = eventsContentProviderQueryFactory.createForDeleteEvent(eventId)

            contentResolver.delete(deleteData.uri, null, null)
        }
    }

    override suspend fun createEvent(newEvent: NewEvent) {
        withContext(dispatcherProvider.io()) {
            val updateData = eventsContentProviderQueryFactory.createForCreateEvent(newEvent)

            val newEventUri = contentResolver.insert(updateData.uri, updateData.values)

            newEvent.reminder?.let { reminder ->
                withContext(NonCancellable) {
                    newEventUri?.lastPathSegment?.toLong()?.let { eventId ->
                        val alertUpdateData =
                            alertsContentProviderQueryFactory.createForNewEvent(reminder, eventId)

                        contentResolver.insert(alertUpdateData.uri, alertUpdateData.values)
                    }
                }
            }
        }
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
