package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.usecases.base.ParameterizedUseCase
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

interface GetDaysWithEventsForMonthUseCase : ParameterizedUseCase<YearMonth, List<LocalDate>>

internal class GetDaysWithEventsForMonthUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : GetDaysWithEventsForMonthUseCase {

    override suspend fun invoke(param: YearMonth): List<LocalDate> =
        eventsRepository.getDaysWithEventsForMonth(param)
}
