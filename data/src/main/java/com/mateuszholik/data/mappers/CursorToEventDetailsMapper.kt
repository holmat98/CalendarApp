package com.mateuszholik.data.mappers

import android.database.Cursor
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.mateuszholik.data.extensions.toLocalDateTime
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_ALL_DAY_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_AVAILABILITY_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_CALENDAR_ID_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_CAN_MODIFY_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_CAN_SEE_GUESTS_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_COLOR_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_DATE_END_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_DATE_START_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_DESCRIPTION_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_HAS_ALARM_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_ID_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_IS_ORGANIZER_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_LOCATION_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_ORGANIZER_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_TIMEZONE_INDEX
import com.mateuszholik.data.factories.EventsContentProviderQueryFactory.Companion.EVENT_TITLE_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.EventDetails
import javax.inject.Inject

internal interface CursorToEventDetailsMapper : Mapper<EventDetails>

internal class CursorToEventDetailsMapperImpl @Inject constructor() : CursorToEventDetailsMapper {

    override suspend fun map(cursor: Cursor): EventDetails {
        cursor.moveToFirst()

        return with(cursor) {
            val timezone = getString(EVENT_TIMEZONE_INDEX)

            EventDetails(
                id = getLong(EVENT_ID_INDEX),
                title = getString(EVENT_TITLE_INDEX),
                description = getStringOrNull(EVENT_DESCRIPTION_INDEX).orEmpty(),
                dateStart = getLong(EVENT_DATE_START_INDEX).toLocalDateTime(timezone),
                dateEnd = getLong(EVENT_DATE_END_INDEX).toLocalDateTime(timezone),
                timezone = timezone,
                allDay = getInt(EVENT_ALL_DAY_INDEX) == 1,
                eventColor = getIntOrNull(EVENT_COLOR_INDEX),
                availability = getInt(EVENT_AVAILABILITY_INDEX),
                location = getStringOrNull(EVENT_LOCATION_INDEX).orEmpty(),
                organizer = getString(EVENT_ORGANIZER_INDEX),
                hasAlarm = getInt(EVENT_HAS_ALARM_INDEX) == 1,
                canModify = canModify(),
                canSeeGuests = getInt(EVENT_CAN_SEE_GUESTS_INDEX) == 1,
                calendarId = getLong(EVENT_CALENDAR_ID_INDEX)
            )
        }
    }

    private fun Cursor.canModify(): Boolean =
        getInt(EVENT_IS_ORGANIZER_INDEX) == 1 || getInt(EVENT_CAN_MODIFY_INDEX) == 1
}
