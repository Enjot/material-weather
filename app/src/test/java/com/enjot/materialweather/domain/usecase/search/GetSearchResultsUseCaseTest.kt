package com.enjot.materialweather.domain.usecase.search

import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSearchResultsUseCaseTest {

    private lateinit var remoteRepository: RemoteRepository
    private lateinit var getSearchResultsUseCase: GetSearchResultsUseCase

    @BeforeEach
    fun setUp() {
        remoteRepository = mockk(relaxed = true)
        getSearchResultsUseCase = GetSearchResultsUseCase(remoteRepository)
    }

    @Test
    fun `Search results, remote repository uses query provided by use case`() = runTest {

        val query = "ABCD"

        coEvery { remoteRepository.getSearchResults(any()) } returns Resource.Success(emptyList())

        getSearchResultsUseCase(query)

        coVerify {
            remoteRepository.getSearchResults(query)
        }

        confirmVerified(remoteRepository)
    }
}