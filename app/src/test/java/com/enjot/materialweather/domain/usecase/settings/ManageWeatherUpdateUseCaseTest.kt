package com.enjot.materialweather.domain.usecase.settings

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.enjot.materialweather.fakes.PreferencesRepositoryFake
import com.enjot.materialweather.fakes.WorkSchedulerFake
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ManageWeatherUpdateUseCaseTest {

    private lateinit var preferencesRepository: PreferencesRepositoryFake
    private lateinit var workScheduler: WorkSchedulerFake
    private lateinit var manageWeatherUpdateUseCase: ManageWeatherUpdateUseCase

    @BeforeEach
    fun setUp() {
        preferencesRepository = PreferencesRepositoryFake()
        workScheduler = WorkSchedulerFake()
        manageWeatherUpdateUseCase = ManageWeatherUpdateUseCase(workScheduler, preferencesRepository)
    }

    @Test
    fun `Update repeat interval, updated correctly`() = runTest {

        val initialInterval =
            preferencesRepository.userPreferences.value.backgroundUpdatesRepeatInterval

        assertThat(initialInterval).isEqualTo(30)

        manageWeatherUpdateUseCase.updateRepeatInterval(60L)

        val newInterval =
            manageWeatherUpdateUseCase.userPreferences.first().backgroundUpdatesRepeatInterval

        assertThat(newInterval).isEqualTo(60)
    }

    @Test
    fun `Schedule update weather work, scheduled correctly`() = runTest {

        val initialWork = workScheduler.updateWeatherWork

        assertThat(initialWork.third).isEqualTo(false)

        manageWeatherUpdateUseCase.schedule()

        val newWork = workScheduler.updateWeatherWork

        assertThat(newWork.third).isEqualTo(true)
    }

    @Test
    fun `Update interval, correctly updated`() = runTest {

        assertThat(
            preferencesRepository.userPreferences.value.backgroundUpdatesRepeatInterval
        ).isEqualTo(30L)

        manageWeatherUpdateUseCase.updateRepeatInterval(120L)

        val newInterval =
            manageWeatherUpdateUseCase.userPreferences.first().backgroundUpdatesRepeatInterval

        assertThat(newInterval).isEqualTo(120L)
    }

    @Test
    fun `Update interval, schedule work, new interval passed`() = runTest {

        val initialWork = workScheduler.updateWeatherWork

        assertThat(initialWork.second).isEqualTo(30L)
        assertThat(initialWork.third).isEqualTo(false)

        manageWeatherUpdateUseCase.updateRepeatInterval(120L)

        manageWeatherUpdateUseCase.schedule()

        val newWork = workScheduler.updateWeatherWork

        assertThat(newWork.second).isEqualTo(120L)
        assertThat(newWork.third).isEqualTo(true)
    }

    @Test
    fun `Check if work is scheduled, works properly`() = runTest {

        val initialWorkStatus = manageWeatherUpdateUseCase.isWorkScheduled("UpdateWeatherWork")

        assertThat(initialWorkStatus).isFalse()

        manageWeatherUpdateUseCase.schedule()

        val newWorkStatus = manageWeatherUpdateUseCase.isWorkScheduled("UpdateWeatherWork")

        assertThat(newWorkStatus).isTrue()
    }

    @Test
    fun `Cancel work, cancelled properly`() = runTest {

        workScheduler.updateWeatherWork = workScheduler.updateWeatherWork.copy(
            first = "UpdateWeatherWork", second = 60L, third = true
        )

        val isWorkEnqueued = manageWeatherUpdateUseCase.isWorkScheduled("UpdateWeatherWork")

        assertThat(isWorkEnqueued).isTrue()

        manageWeatherUpdateUseCase.cancel("UpdateWeatherWork")

        val isWorkEnqueued2 = manageWeatherUpdateUseCase.isWorkScheduled("UpdateWeatherWork")

        assertThat(isWorkEnqueued2).isFalse()
    }

    @Test
    fun `Get user preferences flow, use case collects updates properly`() = runTest {

        manageWeatherUpdateUseCase.userPreferences.test {
            awaitItem() // skip initial

            manageWeatherUpdateUseCase.updateRepeatInterval(120L)

            val interval = awaitItem().backgroundUpdatesRepeatInterval

            assertThat(interval).isEqualTo(120L)
        }
    }
}
