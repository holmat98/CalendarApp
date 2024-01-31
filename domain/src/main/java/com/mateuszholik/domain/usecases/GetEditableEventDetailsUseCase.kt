package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.CalendarRepository
import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.extensions.asResult
import com.mateuszholik.domain.extensions.toCommonModel
import com.mateuszholik.domain.extensions.toEditableEventDetails
import com.mateuszholik.domain.models.EditableEventDetails
import com.mateuszholik.domain.models.Result
import com.mateuszholik.domain.usecases.base.ParameterizedResultUseCase
import javax.inject.Inject

interface GetEditableEventDetailsUseCase : ParameterizedResultUseCase<Long, EditableEventDetails>

internal class GetEditableEventDetailsUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val calendarRepository: CalendarRepository,
) : GetEditableEventDetailsUseCase {

    override suspend fun invoke(param: Long): Result<EditableEventDetails> {
        val eventDetails = eventsRepository.getEventDetails(param)

        val calendar = eventDetails?.let {
            calendarRepository.getCalendar(it.calendarId)?.toCommonModel()
        }

        return eventDetails.asResult { this.toEditableEventDetails(calendar) }
    }
}
