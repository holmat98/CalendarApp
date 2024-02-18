package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.extensions.toDataModel
import com.mateuszholik.domain.models.NewEvent
import com.mateuszholik.domain.usecases.base.UnitParameterizedUseCase
import javax.inject.Inject

interface CreateEventUseCase : UnitParameterizedUseCase<NewEvent>

internal class CreateEventUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : CreateEventUseCase {

    override suspend fun invoke(param: NewEvent) =
        eventsRepository.createEvent(param.toDataModel())
}
