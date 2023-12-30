package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import javax.inject.Inject

internal interface AttendeesContentProviderQueryFactory {

    suspend fun createForAttendeesFromEvent(eventId: Long): QueryData

    companion object {
        const val EVENT_ATTENDEE_ID_INDEX = 0
        const val EVENT_ATTENDEE_EMAIL_INDEX = 1
        const val EVENT_ATTENDEE_NAME_INDEX = 2
        const val EVENT_ATTENDEE_STATUS_INDEX = 3
    }
}

internal class AttendeesContentProviderQueryFactoryImpl @Inject constructor() :
    AttendeesContentProviderQueryFactory {

    override suspend fun createForAttendeesFromEvent(eventId: Long): QueryData =
        QueryData(
            uri = CalendarContract.Attendees.CONTENT_URI,
            selection = "(${CalendarContract.Attendees.EVENT_ID} = ?)",
            selectionArgs = arrayOf("$eventId"),
            projection = arrayOf(
                CalendarContract.Attendees._ID,
                CalendarContract.Attendees.ATTENDEE_EMAIL,
                CalendarContract.Attendees.ATTENDEE_NAME,
                CalendarContract.Attendees.ATTENDEE_STATUS,
            )
        )
}
