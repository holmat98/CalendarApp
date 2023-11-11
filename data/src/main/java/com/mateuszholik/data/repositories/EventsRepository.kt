package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.data.extensions.toEvent
import com.mateuszholik.data.extensions.toList
import com.mateuszholik.data.extensions.toLocalDate
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory
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
            ?.toList { this.toEvent() }
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
            ?.toList { getLong(0).toLocalDate() }
            ?.distinct()
            .orEmpty()

        cursor?.close()

        return days
    }
}
