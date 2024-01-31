package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.CalendarRepository
import com.mateuszholik.domain.extensions.toCommonModel
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.usecases.base.UseCase
import javax.inject.Inject

interface GetCalendarsUseCase : UseCase<List<Calendar>>

internal class GetCalendarsUseCaseImpl @Inject constructor(
    private val calendarRepository: CalendarRepository,
) : GetCalendarsUseCase {

    override suspend fun invoke(): List<Calendar> =
        calendarRepository
            .getCalendars()
            .map { it.toCommonModel() }
}
