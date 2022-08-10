package com.chumachenko.core.data.networking

import com.chumachenko.core.data.response.DrinksListResponse
import retrofit2.http.*

interface AppApi {

//    @POST("auth/code")
//    @FormUrlEncoded
//    suspend fun sendCode(
//        @Field("phone") phone: String,
//        @Field("country_code") countryCode: String,
//        @Field("inviter_user_id") inviterUserId: Int?
//    ): SendCodeResponse

    @GET("random.php")
    suspend fun authWithMagicCode(): DrinksListResponse

}