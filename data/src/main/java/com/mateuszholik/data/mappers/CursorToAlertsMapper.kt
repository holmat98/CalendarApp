package com.mateuszholik.data.mappers

import android.database.Cursor
import com.mateuszholik.data.factories.AlertsContentProviderQueryFactory.Companion.EVENT_ALERTS_MINUTES_INDEX
import com.mateuszholik.data.mappers.base.Mapper
import com.mateuszholik.data.repositories.models.Alert
import javax.inject.Inject

internal interface CursorToAlertsMapper : Mapper<List<Alert>>

internal class CursorToAlertsMapperImpl @Inject constructor() : CursorToAlertsMapper {

    override suspend fun map(cursor: Cursor): List<Alert> =
        List(cursor.count) {
            cursor.moveToPosition(it)

            Alert(minutesBefore = cursor.getString(EVENT_ALERTS_MINUTES_INDEX))
        }
}
