package com.chumachenko.core.domain.interactor

import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.repository.CoreRepository

class CoreInteractor(
    private val repository: CoreRepository
) {

    suspend fun searchDrinks(query: String) =
        repository.searchDrinks(query)

    suspend fun getRecentSearches(): List<SearchResult> {
        return repository.getRecentSearches()
    }

    suspend fun addRecentSearch(item: SearchResult) {
        repository.addRecentSearch(item)
    }

    suspend fun clearRecentSearch() {
        repository.clearRecentSearch()
    }
}