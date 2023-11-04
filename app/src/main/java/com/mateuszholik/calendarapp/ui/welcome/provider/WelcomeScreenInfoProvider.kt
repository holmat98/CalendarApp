package com.mateuszholik.calendarapp.ui.welcome.provider

import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.WelcomeInfo
import java.time.LocalDate
import java.time.Month
import javax.inject.Inject

interface WelcomeScreenInfoProvider {

    fun provide(): WelcomeInfo
}

class WelcomeScreenInfoProviderImpl @Inject constructor(
    private val currentDateProvider: CurrentDateProvider,
) : WelcomeScreenInfoProvider {

    override fun provide(): WelcomeInfo {
        val today = currentDateProvider.provide()
        val currentYear = today.year

        return WelcomeInfo(
            text = getTextResId(today, currentYear),
            image = getDrawableResId(today, currentYear)
        )
    }

    private fun getTextResId(today: LocalDate, currentYear: Int): Int =
        when {
            today == LocalDate.of(currentYear, 1, 1) -> R.string.welcome_screen_hello_new_year
            today == LocalDate.of(currentYear, 3, 20) -> R.string.welcome_screen_hello_spring
            today == LocalDate.of(currentYear, 6, 21) -> R.string.welcome_screen_hello_summer
            today == LocalDate.of(currentYear, 7, 1) -> R.string.welcome_screen_hello_vacation
            today == LocalDate.of(currentYear, 9, 1) -> R.string.welcome_screen_hello_school
            today == LocalDate.of(currentYear, 9, 23) -> R.string.welcome_screen_hello_autumn
            today == LocalDate.of(currentYear, 10, 31) -> R.string.welcome_screen_hello_halloween
            today == LocalDate.of(currentYear, 12, 6) -> R.string.welcome_screen_hello_santa
            today == LocalDate.of(currentYear, 12, 21) -> R.string.welcome_screen_hello_winter
            today == LocalDate.of(currentYear, 12, 24) -> R.string.welcome_screen_hello_christmas
            today == LocalDate.of(currentYear, 12, 31) -> R.string.welcome_screen_hello_new_year_eve
            today.month == Month.JANUARY -> R.string.welcome_screen_hello_january
            today.month == Month.FEBRUARY -> R.string.welcome_screen_hello_february
            today.month == Month.MARCH -> R.string.welcome_screen_hello_march
            today.month == Month.APRIL -> R.string.welcome_screen_hello_april
            today.month == Month.MAY -> R.string.welcome_screen_hello_may
            today.month == Month.JUNE -> R.string.welcome_screen_hello_june
            today.month == Month.JULY -> R.string.welcome_screen_hello_july
            today.month == Month.AUGUST -> R.string.welcome_screen_hello_august
            today.month == Month.SEPTEMBER -> R.string.welcome_screen_hello_september
            today.month == Month.OCTOBER -> R.string.welcome_screen_hello_october
            today.month == Month.NOVEMBER -> R.string.welcome_screen_hello_november
            else -> R.string.welcome_screen_hello_december
        }

    private fun getDrawableResId(today: LocalDate, currentYear: Int): Int =
        when {
            today == LocalDate.of(currentYear, 1, 1) -> R.drawable.ic_new_year
            today == LocalDate.of(currentYear, 3, 20) -> R.drawable.ic_spring_1
            today == LocalDate.of(currentYear, 6, 21) -> R.drawable.ic_summer_3
            today == LocalDate.of(currentYear, 7, 1) -> R.drawable.ic_summer_2
            today == LocalDate.of(currentYear, 9, 1) -> R.drawable.ic_summer_1
            today == LocalDate.of(currentYear, 9, 23) -> R.drawable.ic_autumn_1
            today == LocalDate.of(currentYear, 10, 31) -> R.drawable.ic_halloween
            today == LocalDate.of(currentYear, 12, 6) -> R.drawable.ic_santa_claus
            today == LocalDate.of(currentYear, 12, 21) -> R.drawable.ic_winter_2
            today == LocalDate.of(currentYear, 12, 24) -> R.drawable.ic_merry_christmas
            today == LocalDate.of(currentYear, 12, 31) -> R.drawable.ic_new_year
            today.month == Month.JANUARY -> R.drawable.ic_winter_2
            today.month == Month.FEBRUARY -> R.drawable.ic_winter_3
            today.month == Month.MARCH -> R.drawable.ic_spring_1
            today.month == Month.APRIL -> R.drawable.ic_spring_2
            today.month == Month.MAY -> R.drawable.ic_spring_3
            today.month == Month.JUNE -> R.drawable.ic_summer_1
            today.month == Month.JULY -> R.drawable.ic_summer_4
            today.month == Month.AUGUST -> R.drawable.ic_summer_2
            today.month == Month.SEPTEMBER -> R.drawable.ic_summer_3
            today.month == Month.OCTOBER -> R.drawable.ic_autumn_2
            today.month == Month.NOVEMBER -> R.drawable.ic_autumn_3
            else -> R.drawable.ic_winter_1
        }
}
