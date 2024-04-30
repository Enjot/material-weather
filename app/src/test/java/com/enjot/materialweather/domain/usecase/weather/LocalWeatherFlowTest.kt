package com.enjot.materialweather.domain.usecase.weather

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.enjot.materialweather.domain.util.airPollution
import com.enjot.materialweather.domain.util.current
import com.enjot.materialweather.domain.util.dailyList
import com.enjot.materialweather.domain.util.hourlyList
import com.enjot.materialweather.domain.util.searchResult
import com.enjot.materialweather.domain.util.weatherInfo
import com.enjot.materialweather.fakes.LocalRepositoryFake
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LocalWeatherFlowTest {

    private lateinit var localRepository: LocalRepositoryFake
    private lateinit var localWeatherFlow: LocalWeatherFlow

    @BeforeEach
    fun setUp() {
        localRepository = LocalRepositoryFake()
        localWeatherFlow = LocalWeatherFlow(localRepository)
    }

    @Test
    fun `Emit new value, use case collect it properly`() = runTest {

        localWeatherFlow().test {
            val initialWeather = awaitItem()

            assertThat(initialWeather.place).isNull()
            assertThat(initialWeather.current).isNull()
            assertThat(initialWeather.hourly).isNull()
            assertThat(initialWeather.daily).isNull()
            assertThat(initialWeather.airPollution).isNull()

            localRepository.saveLocalWeather(
                weatherInfo = weatherInfo().copy(
                    place = searchResult(),
                    current = current(),
                    hourly = hourlyList(),
                    daily = dailyList(),
                    airPollution = airPollution()
                )
            )

            val newWeather = awaitItem()

            assertThat(newWeather.place).isNotNull()
            assertThat(newWeather.current).isNotNull()
            assertThat(newWeather.hourly).isNotNull()
            assertThat(newWeather.daily).isNotNull()
            assertThat(newWeather.airPollution).isNotNull()
        }
    }
}