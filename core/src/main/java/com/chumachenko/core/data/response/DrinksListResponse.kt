package com.chumachenko.core.data.response

import com.chumachenko.core.data.model.DrinksList
import com.google.gson.annotations.SerializedName

data class DrinksListResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkResponse>
) {
    fun toModel() = DrinksList(drinks = drinks.map { it.toModel() })
}