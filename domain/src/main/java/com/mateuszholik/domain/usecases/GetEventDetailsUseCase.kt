package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.AlertRepository
import com.mateuszholik.data.repositories.AttendeesRepository
import com.mateuszholik.data.repositories.CalendarRepository
import com.mateuszholik.data.repositories.EventsRepository
import com.mateuszholik.domain.extensions.toCommonModel
import com.mateuszholik.domain.extensions.asResult
import com.mateuszholik.domain.models.EventDetails
import com.mateuszholik.domain.models.Result
import com.mateuszholik.domain.usecases.base.ParameterizedResultUseCase
import javax.inject.Inject

interface GetEventDetailsUseCase : ParameterizedResultUseCase<Long, EventDetails>

internal class GetEventDetailsUseCaseImpl @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val alertRepository: AlertRepository,
    private val attendeesRepository: AttendeesRepository,
    private val calendarRepository: CalendarRepository,
) : GetEventDetailsUseCase {

    override suspend fun invoke(param: Long): Result<EventDetails> {
        val event = eventsRepository.getEventDetails(param)

        val attendees = if (event?.canSeeGuests == true) {
            attendeesRepository
                .getAttendeesForEvent(param)
                .map { it.toCommonModel() }
        } else {
            emptyList()
        }

        val alerts = if (event?.hasAlarm == true) {
            alertRepository
                .getAlertsForEvent(param)
                .map { it.toCommonModel() }
        } else {
            emptyList()
        }

        val calendar = event?.let {
            calendarRepository.getCalendar(it.calendarId)?.toCommonModel()
        }

        return event.asResult {
            this.toCommonModel(
                attendees = attendees,
                alerts = alerts,
                calendar = calendar
            )
        }
    }
}
