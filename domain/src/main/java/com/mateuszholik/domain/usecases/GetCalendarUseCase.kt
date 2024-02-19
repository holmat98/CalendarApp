package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.CalendarRepository
import com.mateuszholik.domain.extensions.toCommonModel
import com.mateuszholik.domain.models.Calendar
import com.mateuszholik.domain.usecases.base.UseCase
import javax.inject.Inject

interface GetCalendarUseCase : UseCase<Calendar>

internal class GetCalendarUseCaseImpl @Inject constructor(
    private val calendarRepository: CalendarRepository,
) : GetCalendarUseCase {

    override suspend fun invoke(): Calendar =
        calendarRepository.getCalendars()
            .first()
            .toCommonModel()
}
