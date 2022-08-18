package com.chumachenko.core.data.networking

import com.chumachenko.core.data.response.DrinksListResponse
import retrofit2.http.*

interface CoreApi {

    @GET("search.php?")
    suspend fun searchDrinks(
        @Query("s") query: String
    ): DrinksListResponse

    @GET("filter.php?")
    suspend fun filterByIngredients(
        @Query("i") query: String
    ): DrinksListResponse

    @GET("lookup.php?")
    suspend fun getDrinkById(
        @Query("i") drinkId: String
    ): DrinksListResponse


}