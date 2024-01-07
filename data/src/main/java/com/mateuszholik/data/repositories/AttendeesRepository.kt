package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToAttendeesMapper
import com.mateuszholik.data.repositories.models.Attendee
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AttendeesRepository {

    suspend fun getAttendeesForEvent(eventId: Long): List<Attendee>
}

internal class AttendeesRepositoryImpl @Inject constructor(
    private val attendeesContentProviderQueryFactory: AttendeesContentProviderQueryFactory,
    private val cursorToAttendeesMapper: CursorToAttendeesMapper,
    private val dispatcherProvider: DispatcherProvider,
    @ApplicationContext context: Context,
) : AttendeesRepository {

    private val contentResolver by lazy { context.contentResolver }

    override suspend fun getAttendeesForEvent(eventId: Long): List<Attendee> {
        val queryData = attendeesContentProviderQueryFactory.createForAttendeesFromEvent(eventId)

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val attendees = cursor
            ?.let { cursorToAttendeesMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return attendees
    }
}
