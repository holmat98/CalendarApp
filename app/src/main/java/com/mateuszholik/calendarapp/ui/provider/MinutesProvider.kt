package com.mateuszholik.calendarapp.ui.provider

import com.mateuszholik.dateutils.Minutes
import javax.inject.Inject

interface MinutesProvider {

    fun provide(): List<Minutes>
}

internal class MinutesProviderImpl @Inject constructor() : MinutesProvider {

    override fun provide(): List<Minutes> =
        listOf(
            Minutes.from(15),
            Minutes.from(30),
            Minutes.from(45),
            Minutes.fromHours(1),
            Minutes.fromHours(2),
            Minutes.fromHours(5),
            Minutes.fromDays(1),
            Minutes.fromDays(7),
        )
}
