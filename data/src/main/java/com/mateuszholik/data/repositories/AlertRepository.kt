package com.mateuszholik.data.repositories

import android.content.Context
import com.mateuszholik.common.provider.DispatcherProvider
import com.mateuszholik.data.factories.AlertsContentProviderQueryFactory
import com.mateuszholik.data.mappers.CursorToAlertsMapper
import com.mateuszholik.data.repositories.models.Alert
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AlertRepository {

    suspend fun getAlertsForEvent(eventId: Long): List<Alert>
}

internal class AlertRepositoryImpl @Inject constructor(
    private val alertsContentProviderQueryFactory: AlertsContentProviderQueryFactory,
    private val cursorToAlertsMapper: CursorToAlertsMapper,
    private val dispatcherProvider: DispatcherProvider,
    @ApplicationContext context: Context,
) : AlertRepository {

    private val contentResolver by lazy { context.contentResolver }

    override suspend fun getAlertsForEvent(eventId: Long): List<Alert> {
        val queryData = alertsContentProviderQueryFactory.createForAlarmsFromEvent(eventId)

        val cursor = withContext(dispatcherProvider.io()) {
            contentResolver.query(
                queryData.uri,
                queryData.projection,
                queryData.selection,
                queryData.selectionArgs,
                null
            )
        }

        val alerts = cursor
            ?.let { cursorToAlertsMapper.map(it) }
            .orEmpty()

        cursor?.close()

        return alerts
    }
}
