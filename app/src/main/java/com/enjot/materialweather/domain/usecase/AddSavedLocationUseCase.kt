package com.enjot.materialweather.domain.usecase

import com.enjot.materialweather.domain.mapper.toSavedLocation
import com.enjot.materialweather.domain.model.SearchResult
import com.enjot.materialweather.domain.repository.LocalRepository
import javax.inject.Inject

class AddSavedLocationUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(searchResult: SearchResult) {
        localRepository.addSavedLocation(searchResult.toSavedLocation())
    }
}