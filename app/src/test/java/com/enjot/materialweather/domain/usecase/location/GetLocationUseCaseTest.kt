package com.enjot.materialweather.domain.usecase.location

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.core.domain.LocationRepository
import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.domain.util.coordinates
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetLocationUseCaseTest {

    private lateinit var locationRepository: LocationRepository

    @BeforeEach
    fun setUp() {
        locationRepository = mockk()
    }

    @Test
    fun `Location repository return value, handle properly`() = runTest {

        val coordinates = coordinates()

        coEvery { locationRepository.getCoordinates() } returns Resource.Success(coordinates)

        val result = locationRepository.getCoordinates()

        assertThat(result.data).isEqualTo(coordinates)
    }
}