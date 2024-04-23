package com.enjot.materialweather.domain.usecase.location

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.domain.repository.LocationRepository
import com.enjot.materialweather.domain.util.coordinates
import com.enjot.materialweather.domain.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetLocationUseCaseTest {

    private lateinit var locationRepository: LocationRepository
    private lateinit var getLocationUseCase: GetLocationUseCase

    @BeforeEach
    fun setUp() {
        locationRepository = mockk()
        getLocationUseCase = GetLocationUseCase(locationRepository)
    }

    @Test
    fun `Location repository return value, handle properly`() = runTest {

        val coordinates = coordinates()

        coEvery { locationRepository.getCoordinates() } returns Resource.Success(coordinates)

        val result = getLocationUseCase()

        assertThat(result.data).isEqualTo(coordinates)
    }
}