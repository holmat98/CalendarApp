package com.mateuszholik.data.factories

import android.provider.CalendarContract
import com.mateuszholik.data.factories.models.QueryData
import javax.inject.Inject

internal interface AlertsContentProviderQueryFactory {

    suspend fun createForAlarmsFromEvent(eventId: Long): QueryData

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
}
