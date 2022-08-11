package com.chumachenko.search.data.repository

import com.chumachenko.core.data.networking.CoreApi

class SearchRepository(
    private val coreApi: CoreApi
) {

    suspend fun lastChooseDrinks() =
        coreApi.lastChooseDrinks().toModel()

}