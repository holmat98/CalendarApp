package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.builders.ContentValuesBuilder.contentValuesBuilder
import com.mateuszholik.data.factories.models.QueryData
import com.mateuszholik.data.factories.models.UpdateData
import javax.inject.Inject

internal interface AlertsContentProviderQueryFactory {

    suspend fun createForAlarmsFromEvent(eventId: Long): QueryData

    suspend fun createForNewEvent(reminder: Int, eventId: Long): UpdateData

    companion object {
        const val EVENT_ALERTS_MINUTES_INDEX = 0
    }
}

internal class AlertsContentProviderQueryFactoryImpl @Inject constructor() :
    AlertsContentProviderQueryFactory {

    override suspend fun createForAlarmsFromEvent(eventId: Long): QueryData =
        QueryData(
            uri = CalendarContract.CalendarAlerts.CONTENT_URI,
            selection = "(${CalendarContract.CalendarAlerts.EVENT_ID} = ?)",
            selectionArgs = arrayOf("$eventId"),
            projection = arrayOf(CalendarContract.CalendarAlerts.MINUTES)
        )

    override suspend fun createForNewEvent(reminder: Int, eventId: Long): UpdateData =
        UpdateData(
            uri = CalendarContract.Reminders.CONTENT_URI,
            values = contentValuesBuilder {
                put(CalendarContract.Reminders.MINUTES, reminder)
                put(CalendarContract.Reminders.EVENT_ID, eventId)
                put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
            }
        )
}
