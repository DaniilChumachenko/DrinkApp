package com.chumachenko.core.data.repository

import com.chumachenko.core.data.networking.CoreApi

class CoreRepository(
    private val coreApi: CoreApi
) {

    suspend fun lastChooseDrinks() =
        coreApi.lastChooseDrinks().toModel()

    suspend fun searchDrinks(query: String) =
        coreApi.searchDrinks(query).toModel()

}