package com.enjot.materialweather.weather.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.enjot.materialweather.core.domain.utils.ErrorType
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.weather.domain.repository.LocalRepository
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import com.enjot.materialweather.weather.domain.usecase.UpdateWeatherUseCase
import com.enjot.materialweather.weather.domain.util.coordinates
import com.enjot.materialweather.weather.domain.util.weatherInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateWeatherUseCaseTest {

    private lateinit var localRepository: LocalRepository
    private lateinit var remoteRepository: RemoteRepository
    private lateinit var updateWeatherUseCase: UpdateWeatherUseCase

    @BeforeEach
    fun setUp() {
        localRepository = mockk(relaxed = true)
        remoteRepository = mockk(relaxed = true)
        updateWeatherUseCase = UpdateWeatherUseCase(
            remoteRepository,
            localRepository
        )
    }

    @Test
    fun `Update weather, returns resource success`() = runTest {

        coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Success(weatherInfo())
        coEvery { localRepository.saveLocalWeather(any()) } returns Unit

        val result = updateWeatherUseCase.invoke(coordinates())

        assertThat(result).isInstanceOf(Resource.Success::class)

        coVerify {
            remoteRepository.fetchWeather(any())
            localRepository.saveLocalWeather(any())
        }
        confirmVerified(remoteRepository, localRepository)
    }

    @Test
    fun `Remote repo returns success but empty body, it isn't saved by local repo`() = runTest {

        coEvery { remoteRepository.fetchWeather(any()) } returns Resource.Success()

        val result = updateWeatherUseCase(coordinates())

        assertThat(result.errorType).isEqualTo(ErrorType.UNKNOWN)

        coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }

        confirmVerified(localRepository)
    }

    @Test
    fun `Remote repo returns error, correct error type returned`() = runTest {

        coEvery { remoteRepository.fetchWeather(any()) } returns
                Resource.Error(ErrorType.NETWORK)

        val result = updateWeatherUseCase(coordinates())

        assertThat(result.errorType).isEqualTo(ErrorType.NETWORK)

        coVerify(exactly = 0) { localRepository.saveLocalWeather(any()) }

        confirmVerified(localRepository)
    }
}