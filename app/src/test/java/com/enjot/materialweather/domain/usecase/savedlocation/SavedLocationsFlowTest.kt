package com.enjot.materialweather.domain.usecase.savedlocation

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.enjot.materialweather.domain.util.savedLocation
import com.enjot.materialweather.fakes.LocalRepositoryFake
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SavedLocationsFlowTest {

    private lateinit var localRepository: LocalRepositoryFake

    @BeforeEach
    fun setUp() {
        localRepository = LocalRepositoryFake()
    }

    @Test
    fun `Get saved locations, collect flow properly`() = runTest {

        val savedLocation1 = savedLocation()
        val savedLocation2 = savedLocation().copy(name = "XYZ")

        localRepository.savedLocations.value.addAll(
            listOf(savedLocation1, savedLocation2)
        )

        val result = localRepository.getSavedLocations().first()

        assertThat(result.size).isEqualTo(2)
        assertThat(result[0]).isEqualTo(savedLocation1)
        assertThat(result[1]).isEqualTo(savedLocation2)
    }

}