package com.mateuszholik.calendarapp.provider

import java.time.LocalDate
import javax.inject.Inject

interface CurrentDateProvider {

    fun provide(): LocalDate
}

internal class CurrentDateProviderImpl @Inject constructor() : CurrentDateProvider {

    override fun provide(): LocalDate = LocalDate.now()
}
