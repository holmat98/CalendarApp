package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.extensions.toCommonModelList
import com.mateuszholik.domain.models.Event
import com.mateuszholik.domain.usecases.base.ParameterizedUseCase
import java.time.LocalDate
import javax.inject.Inject

interface GetEventsForDayUseCase : ParameterizedUseCase<LocalDate, List<Event>>

internal class GetEventsForDayUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : GetEventsForDayUseCase {

    override suspend fun invoke(param: LocalDate): List<Event> =
        eventsRepository.getEventsForDay(param).toCommonModelList()
}
