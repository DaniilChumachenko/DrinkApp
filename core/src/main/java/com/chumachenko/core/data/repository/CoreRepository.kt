package com.chumachenko.core.data.repository

import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.DrinksList
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.networking.CoreApi
import com.chumachenko.core.data.storage.DrinksPreferencesManager
import com.chumachenko.core.data.storage.cache.SearchCache
import com.chumachenko.core.data.storage.database.DrinkDatabase
import com.chumachenko.core.data.storage.database.entity.DrinksHistory
import com.chumachenko.core.data.storage.database.entity.SearchHistory

class CoreRepository(
    private val coreApi: CoreApi,
    private val database: DrinkDatabase,
    private val searchCache: SearchCache
) {

    suspend fun searchDrinks(query: String): DrinksList {
        val cacheData = searchCache.get(query)
        val data = if (cacheData != null) {
            cacheData
        } else {
            val responseData = coreApi.searchDrinks(query).toModel()
            searchCache.put(query, responseData)
            responseData
        }
        return data
    }


    suspend fun getRecentSearches(): List<SearchResult> {
        return database.searchHistoryDao().getLast15().map {
            SearchResult(
                title = it.title
            )
        }
    }

    suspend fun addRecentSearch(item: SearchResult) {
        val currentSearches = database.searchHistoryDao().getAll()
        if (currentSearches.size >= 15 && currentSearches.find { it.title == item.title } == null) {
            database.searchHistoryDao().delete(currentSearches.last())
        } else if (currentSearches.isEmpty()) {
            setNewRecent(item)
        } else {
            var findRecent = false
            currentSearches.forEach {
                if (it.title == item.title) {
                    findRecent = true
                    database.searchHistoryDao().delete(it)
                    setNewRecent(item)
                }
            }
            if (!findRecent)
                setNewRecent(item)
        }
    }

    suspend fun clearRecentSearch() {
        val currentSearches = database.searchHistoryDao().getAll()
        currentSearches.forEach {
            database.searchHistoryDao().delete(it)
        }
        database.drinksHistoryDao().deleteAll()
    }

    suspend fun getLastDrinks() = database.drinksHistoryDao().getLast()

    suspend fun saveOpenDrink(drink: Drink) {
        database.drinksHistoryDao().insert(
            drink.toEntity()
        )
    }

    private suspend fun setNewRecent(item: SearchResult) {
        database.searchHistoryDao().insert(
            SearchHistory(
                title = item.title,
                savedAt = System.currentTimeMillis()
            )
        )
    }
}