package com.mateuszholik.calendarapp.ui.calendar

import app.cash.turbine.test
import com.mateuszholik.calendarapp.provider.CurrentDateProvider
import com.mateuszholik.calendarapp.TestDispatcherProvider
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiEvent
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUiState
import com.mateuszholik.calendarapp.ui.calendar.CalendarViewModel.CalendarUserAction
import com.mateuszholik.domain.models.Event
import com.mateuszholik.domain.usecases.GetDaysWithEventsForMonthUseCase
import com.mateuszholik.domain.usecases.GetEventsForDayUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalCoroutinesApi::class)
internal class CalendarViewModelTest {

    private val getEventsForDayUseCase = mockk<GetEventsForDayUseCase> {
        coEvery { this@mockk.invoke(CURRENT_DATE) } returns emptyList()
    }
    private val getDaysWithEventsForMonthUseCase = mockk<GetDaysWithEventsForMonthUseCase> {
        coEvery { this@mockk.invoke(CURRENT_MONTH) } returns emptyList()
    }
    private val dispatcherProvider = TestDispatcherProvider()
    private val currentDateProvider = mockk<CurrentDateProvider> {
        every { provide() } returns CURRENT_DATE
    }

    private lateinit var viewModel: CalendarViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CalendarViewModel(
            getEventsForDayUseCase = getEventsForDayUseCase,
            getDaysWithEventsForMonthUseCase = getDaysWithEventsForMonthUseCase,
            dispatcherProvider = dispatcherProvider,
            currentDateProvider = currentDateProvider,
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `After creating CalendarViewModel uiState initial value are set to Loading`() =
        runTest {
            assertThat(viewModel.uiState.value).isEqualTo(
                CalendarUiState.Loading(CURRENT_DATE, CURRENT_MONTH)
            )
        }

    @Test
    fun `After creating CalendarViewModel and calling RefreshScreen user action uiState value is equal to CalendarInfo`() =
        runTest {
            viewModel.performUserAction(CalendarUserAction.RefreshScreen)

            assertThat(viewModel.uiState.value).isEqualTo(
                CalendarUiState.CalendarInfo(
                    currentDate = CURRENT_DATE,
                    currentMonth = CURRENT_MONTH,
                    events = emptyList(),
                    daysWithEvents = emptyList()
                )
            )

            verify(exactly = 1) { currentDateProvider.provide() }
            coVerify(exactly = 1) { getEventsForDayUseCase(CURRENT_DATE) }
            coVerify(exactly = 1) { getDaysWithEventsForMonthUseCase(CURRENT_MONTH) }
        }

    @Test
    fun `When SelectedDateChanged user action is performed uiState emits new value`() =
        runTest {
            viewModel.performUserAction(CalendarUserAction.RefreshScreen)
            coEvery { getEventsForDayUseCase(NEW_DATE) } returns EVENTS

            viewModel.performUserAction(CalendarUserAction.SelectedDateChanged(NEW_DATE))

            assertThat(viewModel.uiState.value).isEqualTo(
                CalendarUiState.CalendarInfo(
                    currentDate = NEW_DATE,
                    currentMonth = CURRENT_MONTH,
                    events = EVENTS,
                    daysWithEvents = emptyList()
                )
            )

            coVerify(exactly = 1) { getEventsForDayUseCase(NEW_DATE) }
        }

    @Test
    fun `When SelectedDateChanged user action is performed and error occurred then uiEvent emits Error value`() =
        runTest {
            viewModel.performUserAction(CalendarUserAction.RefreshScreen)
            coEvery { getEventsForDayUseCase(NEW_DATE) } throws Exception("Something went wrong")

            viewModel.uiEvent.test {
                viewModel.performUserAction(CalendarUserAction.SelectedDateChanged(NEW_DATE))

                val emittedItem = awaitItem()

                assertThat(emittedItem).isEqualTo(CalendarUiEvent.Error)
            }

            coVerify(exactly = 1) { getEventsForDayUseCase(NEW_DATE) }
        }

    @Test
    fun `When CurrentMonthChanged user action is performed uiState emits new value`() =
        runTest {
            viewModel.performUserAction(CalendarUserAction.RefreshScreen)
            coEvery { getDaysWithEventsForMonthUseCase(NEW_MONTH) } returns DAYS_WITH_EVENTS

            viewModel.performUserAction(CalendarUserAction.CurrentMonthChanged(NEW_MONTH))

            assertThat(viewModel.uiState.value).isEqualTo(
                CalendarUiState.CalendarInfo(
                    currentDate = CURRENT_DATE,
                    currentMonth = NEW_MONTH,
                    events = emptyList(),
                    daysWithEvents = DAYS_WITH_EVENTS
                )
            )

            coVerify(exactly = 1) { getDaysWithEventsForMonthUseCase(NEW_MONTH) }
        }

    @Test
    fun `When CurrentMonthChanged user action is performed and error occurred then uiEvent emits Error value`() =
        runTest {
            viewModel.performUserAction(CalendarUserAction.RefreshScreen)
            coEvery { getDaysWithEventsForMonthUseCase(NEW_MONTH) } throws Exception("Something went wrong")

            viewModel.uiEvent.test {
                viewModel.performUserAction(CalendarUserAction.CurrentMonthChanged(NEW_MONTH))

                val emittedItem = awaitItem()

                assertThat(emittedItem).isEqualTo(CalendarUiEvent.Error)
            }

            coVerify(exactly = 1) { getDaysWithEventsForMonthUseCase(NEW_MONTH) }
        }

    @Test
    fun `When EventClicked user action is performed uiEvent emits NavigateToEvent value`() =
        runTest {
            viewModel.uiEvent.test {
                viewModel.performUserAction(CalendarUserAction.EventClicked(eventId = 1))

                val emittedItem = awaitItem()

                assertThat(emittedItem).isEqualTo(CalendarUiEvent.NavigateToEvent(1))
            }
        }

    @Test
    fun `When AddEventClicked user action is performed uiEvent emits NavigateToAddEvent value`() =
        runTest {
            viewModel.uiEvent.test {
                viewModel.performUserAction(CalendarUserAction.AddEventClicked)
                val emittedItem = awaitItem()

                assertThat(emittedItem).isEqualTo(CalendarUiEvent.NavigateToAddEvent)
            }
        }

    private companion object {
        val CURRENT_DATE: LocalDate = LocalDate.of(2023, 11, 18)
        val NEW_DATE: LocalDate = LocalDate.of(2023, 11, 21)
        val CURRENT_MONTH: YearMonth = YearMonth.of(2023, 11)
        val NEW_MONTH: YearMonth = YearMonth.of(2023, 12)
        val DAYS_WITH_EVENTS: List<LocalDate> = listOf(
            LocalDate.of(2023, 11, 1),
            LocalDate.of(2023, 11, 3),
            LocalDate.of(2023, 11, 21),
        )
        val EVENTS: List<Event> = listOf(
            Event(
                id = 1L,
                title = "Event",
                startDate = NEW_DATE.atTime(15, 0, 0),
                endDate = NEW_DATE.atTime(16, 0, 0),
                color = null,
                allDay = false,
            )
        )
    }
}
