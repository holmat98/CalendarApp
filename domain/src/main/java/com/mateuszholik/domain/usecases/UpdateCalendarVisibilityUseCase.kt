package com.mateuszholik.domain.usecases

import com.mateuszholik.data.repositories.CalendarRepository
import com.mateuszholik.domain.usecases.base.UnitParameterizedUseCase
import com.mateuszholik.domain.usecases.UpdateCalendarVisibilityUseCase.Param
import javax.inject.Inject

interface UpdateCalendarVisibilityUseCase : UnitParameterizedUseCase<Param> {

    data class Param(
        val calendarId: Long,
        val isVisible: Boolean,
    )
}

internal class UpdateCalendarVisibilityUseCaseImpl @Inject constructor(
    private val calendarRepository: CalendarRepository,
) : UpdateCalendarVisibilityUseCase {

    override suspend fun invoke(param: Param) {
        calendarRepository.changeCalendarVisibility(
            calendarId = param.calendarId,
            isVisible = param.isVisible,
        )
    }
}
