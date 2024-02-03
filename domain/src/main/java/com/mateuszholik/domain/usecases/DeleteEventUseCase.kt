package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.usecases.base.UnitParameterizedUseCase
import javax.inject.Inject

interface DeleteEventUseCase : UnitParameterizedUseCase<Long>

internal class DeleteEventUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : DeleteEventUseCase {

    override suspend fun invoke(param: Long) {
        eventsRepository.deleteEvent(param)
    }
}
