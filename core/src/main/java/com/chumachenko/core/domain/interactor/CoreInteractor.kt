package com.chumachenko.core.domain.interactor

import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.repository.CoreRepository
import com.chumachenko.core.data.storage.database.entity.DrinksHistory

class CoreInteractor(
    private val repository: CoreRepository
) {

    suspend fun searchDrinks(query: String) =
        repository.searchDrinks(query)

    suspend fun filterByIngredients(query: String) =
        repository.filterByIngredients(query)

    suspend fun getRecentSearches(): List<SearchResult> = repository.getRecentSearches()

    suspend fun addRecentSearch(item: SearchResult) = repository.addRecentSearch(item)

    suspend fun clearRecentSearch() = repository.clearRecentSearch()

    suspend fun getLastDrinks() = repository.getLastDrinks()
}