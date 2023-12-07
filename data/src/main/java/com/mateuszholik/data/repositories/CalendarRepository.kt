package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToCalendarsMapper
import com.mateuszholik.data.repositories.models.Calendar
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CalendarRepository {

    suspend fun getCalendars(): List<Calendar>
}

internal class CalendarRepositoryImpl @Inject constructor(
    private val calendarContentProviderQueryFactory: CalendarContentProviderQueryFactory,
    private val cursorToCalendarsMapper: CursorToCalendarsMapper,
    private val dispatcherProvider: DispatcherProvider,
    @ApplicationContext context: Context,
) : CalendarRepository {

    private val contentResolver by lazy { context.contentResolver }

    override suspend fun getCalendars(): List<Calendar> {
        val queryData = calendarContentProviderQueryFactory.createForCalendars()

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val calendars = cursor
            ?.let { cursorToCalendarsMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return calendars
    }
}
