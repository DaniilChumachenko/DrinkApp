package com.chumachenko.search.data.domain.interactor

import com.chumachenko.core.data.repository.CoreRepository

class SearchInteractor(
    private val repository: CoreRepository
) {

    suspend fun lastChooseDrinks() =
        repository.lastChooseDrinks()

}