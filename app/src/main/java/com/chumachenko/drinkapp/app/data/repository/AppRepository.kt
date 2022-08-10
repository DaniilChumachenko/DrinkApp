package com.chumachenko.drinkapp.app.data.repository

import com.chumachenko.core.data.networking.AppApi

class AppRepository(
    private val authApi: AppApi
) {

    suspend fun updateUserInfo() =
        authApi.authWithMagicCode()

}