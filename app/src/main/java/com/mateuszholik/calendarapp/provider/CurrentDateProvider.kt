package com.mateuszholik.calendarapp.provider

import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

interface CurrentDateProvider {

    fun provide(): LocalDate

    fun provideDateTime(): LocalDateTime
}

internal class CurrentDateProviderImpl @Inject constructor() : CurrentDateProvider {

    override fun provide(): LocalDate = LocalDate.now()

    override fun provideDateTime(): LocalDateTime = LocalDateTime.now()
}
