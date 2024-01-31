package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.data.factories.CalendarContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToCalendarMapper
import com.mateuszholik.data.mappers.CursorToCalendarsMapper
import com.mateuszholik.data.repositories.models.Calendar
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CalendarRepository {

    suspend fun getCalendars(): List<Calendar>

    suspend fun getCalendar(calendarId: Long): Calendar?

    suspend fun changeCalendarVisibility(
        calendarId: Long,
        isVisible: Boolean,
    )
}

internal class CalendarRepositoryImpl @Inject constructor(
    private val calendarContentProviderQueryFactory: CalendarContentProviderQueryFactory,
    private val cursorToCalendarsMapper: CursorToCalendarsMapper,
    private val cursorToCalendarMapper: CursorToCalendarMapper,
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

    override suspend fun getCalendar(calendarId: Long): Calendar? {
        val queryData = calendarContentProviderQueryFactory.createForCalendarWith(calendarId)

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val calendar = cursor?.let { cursorToCalendarMapper.map(it) }

        cursor?.close()

        return calendar
    }

    override suspend fun changeCalendarVisibility(calendarId: Long, isVisible: Boolean) {
        withContext(dispatcherProvider.io()) {
            val updateData = calendarContentProviderQueryFactory.createForUpdateCalendarVisibility(
                calendarId = calendarId,
                isVisible = isVisible
            )

            contentResolver.update(updateData.uri, updateData.values, null, null)
        }
    }
}
