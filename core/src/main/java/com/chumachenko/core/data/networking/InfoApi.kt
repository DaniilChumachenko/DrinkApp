package com.chumachenko.core.data.networking

import com.chumachenko.core.data.response.DrinksListResponse
import retrofit2.http.*

interface InfoApi {

    @GET("lookup.php?")
    suspend fun getDrinkById(
        @Query("i") drinkId: String
    ): DrinksListResponse

}