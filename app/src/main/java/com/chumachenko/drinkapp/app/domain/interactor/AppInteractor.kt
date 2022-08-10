package com.chumachenko.drinkapp.app.domain.interactor

import com.chumachenko.drinkapp.app.data.repository.AppRepository

class AppInteractor(
    private val repository: AppRepository
) {

    suspend fun updateUserInfo() =
        repository.updateUserInfo()

}