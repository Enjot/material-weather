package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.repository.RemoteRepository
import com.enjot.materialweather.domain.utils.Resource
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(query: String): Resource<List<SearchResult>> =
        remoteRepository.getSearchResults(query)
}
