package com.chumachenko.core.data.networking

import com.chumachenko.core.data.response.DrinksListResponse
import retrofit2.http.*

interface CoreApi {

//    @POST("auth/code")
//    @FormUrlEncoded
//    suspend fun sendCode(
//        @Field("phone") phone: String,
//        @Field("country_code") countryCode: String,
//        @Field("inviter_user_id") inviterUserId: Int?
//    ): SendCodeResponse

    @GET("search.php?f=m")
    suspend fun lastChooseDrinks(): DrinksListResponse

    @GET("search.php?")
    suspend fun searchDrinks(
        @Query("s") query: String
    ): DrinksListResponse

}