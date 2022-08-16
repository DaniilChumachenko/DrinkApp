package com.chumachenko.core.data.response

import com.chumachenko.core.data.model.DrinksList
import com.google.gson.annotations.SerializedName

data class FullDrinksListResponse(
    @SerializedName("drinks")
    val drinks: List<FullDrinkResponse>
) {
//    fun toModel() = DrinksList(drinks = drinks.map { it.toModel() })
}