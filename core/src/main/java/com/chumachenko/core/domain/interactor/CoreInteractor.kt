package com.chumachenko.core.domain.interactor

import com.chumachenko.core.data.repository.CoreRepository

class CoreInteractor(
    private val repository: CoreRepository
) {

    suspend fun lastChooseDrinks() =
        repository.lastChooseDrinks()

}