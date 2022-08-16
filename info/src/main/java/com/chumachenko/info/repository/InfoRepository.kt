package com.chumachenko.info.repository

import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.DrinksList
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.core.data.networking.CoreApi
import com.chumachenko.core.data.networking.InfoApi
import com.chumachenko.core.data.response.DrinkResponse
import com.chumachenko.core.data.storage.cache.SearchCache
import com.chumachenko.core.data.storage.database.DrinkDatabase
import com.chumachenko.core.data.storage.database.entity.SearchHistory

class InfoRepository(
    private val infoApi: InfoApi,
    private val database: DrinkDatabase,
    private val searchCache: SearchCache
) {

    suspend fun getDrinkById(drinkId: String): DrinkResponse =
        infoApi.getDrinkById(drinkId).drinks.first()

}