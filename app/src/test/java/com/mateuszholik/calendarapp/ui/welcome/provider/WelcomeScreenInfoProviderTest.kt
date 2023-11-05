package com.mateuszholik.calendarapp.ui.welcome.provider

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import io.mockk.mockk
import com.mateuszholik.calendarapp.R
import com.mateuszholik.calendarapp.ui.welcome.WelcomeViewModel.WelcomeScreenState.WelcomeInfo
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.time.LocalDate

internal class WelcomeScreenInfoProviderTest {

    private val currentDateProvider = mockk<CurrentDateProvider>()

    private val welcomeScreenInfoProvider = WelcomeScreenInfoProviderImpl(currentDateProvider)

    @TestFactory
    fun checkWelcomeScreenInfoProvider() =
        listOf(
            TestObject(
                date = LocalDate.of(2023, 1, 1),
                stringResId = R.string.welcome_screen_hello_new_year,
                drawableResId = R.drawable.ic_new_year,
                testName = "text = welcome_screen_hello_new_year, image = and ic_new_year"
            ),
            TestObject(
                date = LocalDate.of(2023, 3, 20),
                stringResId = R.string.welcome_screen_hello_spring,
                drawableResId = R.drawable.ic_spring_1,
                testName = "text = welcome_screen_hello_spring, image = ic_spring_1"
            ),
            TestObject(
                date = LocalDate.of(2023, 6, 21),
                stringResId = R.string.welcome_screen_hello_summer,
                drawableResId = R.drawable.ic_summer_3,
                testName = "text = welcome_screen_hello_summer, image = ic_summer_3"
            ),
            TestObject(
                date = LocalDate.of(2023, 7, 1),
                stringResId = R.string.welcome_screen_hello_vacation,
                drawableResId = R.drawable.ic_summer_2,
                testName = "text = welcome_screen_hello_vacation, image = ic_summer_2"
            ),
            TestObject(
                date = LocalDate.of(2023, 9, 1),
                stringResId = R.string.welcome_screen_hello_school,
                drawableResId = R.drawable.ic_summer_1,
                testName = "text = welcome_screen_hello_school, image = ic_summer_1"
            ),
            TestObject(
                date = LocalDate.of(2023, 9, 23),
                stringResId = R.string.welcome_screen_hello_autumn,
                drawableResId = R.drawable.ic_autumn_1,
                testName = "text = welcome_screen_hello_autumn, image = ic_autumn_1"
            ),
            TestObject(
                date = LocalDate.of(2023, 10, 31),
                stringResId = R.string.welcome_screen_hello_halloween,
                drawableResId = R.drawable.ic_halloween,
                testName = "text = welcome_screen_hello_halloween, image = ic_halloween"
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 6),
                stringResId = R.string.welcome_screen_hello_santa,
                drawableResId = R.drawable.ic_santa_claus,
                testName = "text = welcome_screen_hello_santa, image = ic_santa_claus"
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 21),
                stringResId = R.string.welcome_screen_hello_winter,
                drawableResId = R.drawable.ic_winter_2,
                testName = "text = welcome_screen_hello_winter, image = ic_winter_2"
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 24),
                stringResId = R.string.welcome_screen_hello_christmas,
                drawableResId = R.drawable.ic_merry_christmas,
                testName = "text = welcome_screen_hello_christmas, image = ic_merry_christmas"
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 31),
                stringResId = R.string.welcome_screen_hello_new_year_eve,
                drawableResId = R.drawable.ic_new_year,
                testName = "text = welcome_screen_hello_new_year_eve, image = ic_new_year"
            ),
            TestObject(
                date = LocalDate.of(2023, 1, 21),
                stringResId = R.string.welcome_screen_hello_january,
                drawableResId = R.drawable.ic_winter_2,
                testName = "text = welcome_screen_hello_january, image = ic_winter_2"
            ),
            TestObject(
                date = LocalDate.of(2023, 2, 10),
                stringResId = R.string.welcome_screen_hello_february,
                drawableResId = R.drawable.ic_winter_3,
                testName = "text = welcome_screen_hello_february, image = ic_winter_3"
            ),
            TestObject(
                date = LocalDate.of(2023, 3, 8),
                stringResId = R.string.welcome_screen_hello_march,
                drawableResId = R.drawable.ic_spring_1,
                testName = "text = welcome_screen_hello_march, image = ic_spring_1"
            ),
            TestObject(
                date = LocalDate.of(2023, 4, 21),
                stringResId = R.string.welcome_screen_hello_april,
                drawableResId = R.drawable.ic_spring_2,
                testName = "text = welcome_screen_hello_april, image = ic_spring_2"
            ),
            TestObject(
                date = LocalDate.of(2023, 5, 1),
                stringResId = R.string.welcome_screen_hello_may,
                drawableResId = R.drawable.ic_spring_3,
                testName = "text = welcome_screen_hello_may, image = ic_spring_3"
            ),
            TestObject(
                date = LocalDate.of(2023, 6, 5),
                stringResId = R.string.welcome_screen_hello_june,
                drawableResId = R.drawable.ic_summer_1,
                testName = "text = welcome_screen_hello_june, image = ic_summer_1"
            ),
            TestObject(
                date = LocalDate.of(2023, 7, 21),
                stringResId = R.string.welcome_screen_hello_july,
                drawableResId = R.drawable.ic_summer_4,
                testName = "text = welcome_screen_hello_july, image = ic_summer_4"
            ),
            TestObject(
                date = LocalDate.of(2023, 8, 2),
                stringResId = R.string.welcome_screen_hello_august,
                drawableResId = R.drawable.ic_summer_2,
                testName = "text = welcome_screen_hello_august, image = ic_summer_2"
            ),
            TestObject(
                date = LocalDate.of(2023, 9, 12),
                stringResId = R.string.welcome_screen_hello_september,
                drawableResId = R.drawable.ic_summer_3,
                testName = "text = welcome_screen_hello_september, image = ic_summer_3"
            ),
            TestObject(
                date = LocalDate.of(2023, 10, 6),
                stringResId = R.string.welcome_screen_hello_october,
                drawableResId = R.drawable.ic_autumn_2,
                testName = "text = welcome_screen_hello_october, image = ic_autumn_2"
            ),
            TestObject(
                date = LocalDate.of(2023, 11, 2),
                stringResId = R.string.welcome_screen_hello_november,
                drawableResId = R.drawable.ic_autumn_3,
                testName = "text = welcome_screen_hello_november, image = ic_autumn_3"
            ),
            TestObject(
                date = LocalDate.of(2023, 12, 14),
                stringResId = R.string.welcome_screen_hello_december,
                drawableResId = R.drawable.ic_winter_1,
                testName = "text = welcome_screen_hello_december, image = ic_winter_1"
            ),
        ).map {
            dynamicTest("When the date is ${it.date} then the provider returns WelcomeInfo(${it.testName})") {
                every { currentDateProvider.provide() } returns it.date

                val result = welcomeScreenInfoProvider.provide()

                assertThat(result).isEqualTo(
                    WelcomeInfo(
                        text = it.stringResId,
                        image = it.drawableResId
                    )
                )
            }
        }

    private data class TestObject(
        val date: LocalDate,
        @StringRes val stringResId: Int,
        @DrawableRes val drawableResId: Int,
        val testName: String,
    )
}
