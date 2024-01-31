package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.extensions.toDataModel
import com.mateuszholik.domain.models.UpdatedEventDetails
import com.mateuszholik.domain.usecases.base.UnitParameterizedUseCase
import javax.inject.Inject

interface UpdateEventDetailsUseCase : UnitParameterizedUseCase<UpdatedEventDetails>

internal class UpdateEventDetailsUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
) : UpdateEventDetailsUseCase {

    override suspend fun invoke(param: UpdatedEventDetails) {
        eventsRepository.updateEventDetails(param.toDataModel())
    }
}
