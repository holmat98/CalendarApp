package com.mateuszholik.calendarapp.ui.welcome

import app.cash.turbine.test
import com.mateuszholik.calendarapp.TestDispatcherProvider
import com.mateuszholik.calendarapp.permissions.CalendarPermissionsManager
import com.mateuszholik.calendarapp.ui.welcome.provider.WelcomeScreenInfoProvider
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest {

    private val dispatcherProvider = TestDispatcherProvider()
    private val welcomeScreenInfoProvider = mockk<WelcomeScreenInfoProvider> {
        every { provide() } returns DEFAULT_UI_STATE_VALUE
    }
    private val calendarPermissionsManager = mockk<CalendarPermissionsManager> {
        every { arePermissionsGranted() } returns true
    }

    private lateinit var viewModel: WelcomeViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = WelcomeViewModel(
            dispatcherProvider = dispatcherProvider,
            welcomeScreenInfoProvider = welcomeScreenInfoProvider,
            calendarPermissionsManager = calendarPermissionsManager
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `After initializing viewModel uiState emits WelcomeScreenUiState object`() = runTest {
        assertThat(viewModel.uiState.value).isEqualTo(DEFAULT_UI_STATE_VALUE)
    }

    @Test
    fun `After 2 seconds from initializing viewModel uiEvent emits NavigateToNextScreen event`() =
        runTest {
            viewModel.uiEvent.test {
                val emittedValue = awaitItem()

                assertThat(emittedValue).isEqualTo(WelcomeViewModel.WelcomeScreenUiEvent.NavigateToNextScreen)
            }
        }

    private companion object {
        val DEFAULT_UI_STATE_VALUE = WelcomeViewModel.WelcomeScreenUiState(
            text = 0,
            image = 1,
        )
    }
}
