package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToEventsMapper
import com.mateuszholik.data.mappers.CursorToListOfDaysMapper
import com.mateuszholik.data.repositories.models.Event
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

interface EventsRepository {

    suspend fun getEventsForDay(day: LocalDate): List<Event>

    suspend fun getDaysWithEventsForMonth(yearMonth: YearMonth): List<LocalDate>
}

internal class EventsRepositoryImpl @Inject constructor(
    private val eventsContentProviderQueryFactory: EventsContentProviderQueryFactory,
    private val cursorToEventsMapper: CursorToEventsMapper,
    private val cursorToListOfDaysMapper: CursorToListOfDaysMapper,
    @ApplicationContext private val context: Context,
) : EventsRepository {

    private val contentResolver by lazy { context.contentResolver }

    override suspend fun getEventsForDay(day: LocalDate): List<Event> {
        val queryData = eventsContentProviderQueryFactory.createForEventsFromDay(day)

        val cursor = contentResolver.query(
            queryData.uri,
            queryData.projection,
            queryData.selection,
            queryData.selectionArgs,
            null
        )

        val events = cursor
            ?.let { cursorToEventsMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return events
    }

    override suspend fun getDaysWithEventsForMonth(yearMonth: YearMonth): List<LocalDate> {
        val queryData = eventsContentProviderQueryFactory.createForEventsFromMonth(yearMonth)

        val cursor = contentResolver.query(
            queryData.uri,
            queryData.projection,
            queryData.selection,
            queryData.selectionArgs,
            null
        )

        val days = cursor
            ?.let { cursorToListOfDaysMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return days
    }
}
