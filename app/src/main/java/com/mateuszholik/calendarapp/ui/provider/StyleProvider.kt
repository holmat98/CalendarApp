package com.mateuszholik.calendarapp.ui.provider

import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.designsystem.models.StyleType
import java.time.Month
import javax.inject.Inject

interface StyleProvider {

    fun provide(): StyleType
}

class StyleProviderImpl @Inject constructor(
    private val currentDateProvider: CurrentDateProvider,
) : StyleProvider {

    override fun provide(): StyleType =
        when (currentDateProvider.provide().month) {
            Month.MARCH,
            Month.APRIL,
            Month.MAY -> StyleType.SPRING
            Month.JUNE,
            Month.JULY,
            Month.AUGUST -> StyleType.SUMMER
            Month.SEPTEMBER,
            Month.OCTOBER,
            Month.NOVEMBER -> StyleType.AUTUMN
            else -> StyleType.WINTER
        }
}
