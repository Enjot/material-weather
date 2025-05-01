package com.enjot.materialweather.domain.usecase.search

import com.enjot.materialweather.core.domain.utils.Resource
import com.enjot.materialweather.weather.domain.repository.RemoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSearchResultsUseCaseTest {

    private lateinit var remoteRepository: RemoteRepository

    @BeforeEach
    fun setUp() {
        remoteRepository = mockk(relaxed = true)
    }

    @Test
    fun `Search results, remote repository uses query provided by use case`() = runTest {

        val query = "ABCD"

        coEvery { remoteRepository.getSearchResults(any()) } returns Resource.Success(emptyList())

        remoteRepository.getSearchResults(query)

        coVerify {
            remoteRepository.getSearchResults(query)
        }

        confirmVerified(remoteRepository)
    }
}