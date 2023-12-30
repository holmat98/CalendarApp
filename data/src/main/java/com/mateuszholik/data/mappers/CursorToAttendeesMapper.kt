package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactory.Companion.EVENT_ATTENDEE_EMAIL_INDEX
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactory.Companion.EVENT_ATTENDEE_ID_INDEX
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactory.Companion.EVENT_ATTENDEE_NAME_INDEX
import com.mateuszholik.data.factories.AttendeesContentProviderQueryFactory.Companion.EVENT_ATTENDEE_STATUS_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.Attendee
import javax.inject.Inject

internal interface CursorToAttendeesMapper : Mapper<List<Attendee>>

internal class CursorToAttendeesMapperImpl @Inject constructor() : CursorToAttendeesMapper {

    override suspend fun map(cursor: Cursor): List<Attendee> =
        List(cursor.count) {
            cursor.moveToPosition(it)
            cursor.toAttendee()
        }

    private fun Cursor.toAttendee(): Attendee =
        Attendee(
            id = getLong(EVENT_ATTENDEE_ID_INDEX),
            email = getString(EVENT_ATTENDEE_EMAIL_INDEX),
            name = getString(EVENT_ATTENDEE_NAME_INDEX),
            status = getInt(EVENT_ATTENDEE_STATUS_INDEX)
        )
}
