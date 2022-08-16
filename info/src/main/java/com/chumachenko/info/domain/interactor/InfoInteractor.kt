package com.chumachenko.info.domain.interactor

import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult
import com.chumachenko.info.repository.InfoRepository

class InfoInteractor(
    private val repository: InfoRepository
) {

    suspend fun getDrinkById(drinkId: String) =
        repository.getDrinkById(drinkId).toModel()

    suspend fun saveOpenDrink(drink: Drink) = repository.saveOpenDrink(drink)

}