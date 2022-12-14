package com.chumachenko.core.data.response

import com.chumachenko.core.data.model.Drink
import com.google.gson.annotations.SerializedName

data class DrinkResponse(
    @SerializedName("dateModified")
    val dateModified: String?,
    @SerializedName("idDrink")
    val idDrink: String?,
    @SerializedName("strAlcoholic")
    val strAlcoholic: String?,
    @SerializedName("strCategory")
    val strCategory: String?,
    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,
    @SerializedName("strDrink")
    val strDrink: String?,
    @SerializedName("strInstructions")
    val strInstructions: String?,
    @SerializedName("strDrinkAlternate")
    val strDrinkAlternate: String?,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String?,
    @SerializedName("strIngredient1")
    val strIngredient1: String?,
    @SerializedName("strIngredient2")
    val strIngredient2: String?,
    @SerializedName("strIngredient3")
    val strIngredient3: String?,
    @SerializedName("strIngredient4")
    val strIngredient4: String?,
    @SerializedName("strIngredient5")
    val strIngredient5: String?,
    @SerializedName("strIngredient6")
    val strIngredient6: String?,
    @SerializedName("strIngredient7")
    val strIngredient7: String?,
    @SerializedName("strIngredient8")
    val strIngredient8: String?,
    @SerializedName("strIngredient9")
    val strIngredient9: String?,
    @SerializedName("strIngredient10")
    val strIngredient10: String?,
    @SerializedName("strIngredient11")
    val strIngredient11: String?,
    @SerializedName("strIngredient12")
    val strIngredient12: String?,
    @SerializedName("strIngredient13")
    val strIngredient13: String?,
    @SerializedName("strIngredient14")
    val strIngredient14: String?,
    @SerializedName("strIngredient15")
    val strIngredient15: String?,
    @SerializedName("strGlass")
    val strGlass: String?,
    @SerializedName("strIBA")
    val strIBA: String?,
    @SerializedName("strImageAttribution")
    val strImageAttribution: String?,
    @SerializedName("strImageSource")
    val strImageSource: String?,
    @SerializedName("strTags")
    val strTags: String?,
    @SerializedName("strVideo")
    val strVideo: String?
) {
    fun toModel() =
        Drink(
            dateModified = dateModified ?: "",
            idDrink = idDrink ?: "",
            strAlcoholic = strAlcoholic ?: "",
            strCategory = strCategory ?: "",
            strCreativeCommonsConfirmed = strCreativeCommonsConfirmed ?: "",
            strDrink = strDrink ?: "",
            strDrinkAlternate = strDrinkAlternate ?: "",
            strInstructions = strInstructions ?: "",
            strDrinkThumb = strDrinkThumb ?: "",
            strIngredient1 = strIngredient1 ?: "",
            strIngredient2 = strIngredient2 ?: "",
            strIngredient3 = strIngredient3 ?: "",
            strIngredient4 = strIngredient4 ?: "",
            strIngredient5 = strIngredient5 ?: "",
            strIngredient6 = strIngredient6 ?: "",
            strIngredient7 = strIngredient7 ?: "",
            strIngredient8 = strIngredient8 ?: "",
            strIngredient9 = strIngredient9 ?: "",
            strIngredient10 = strIngredient10 ?: "",
            strIngredient11 = strIngredient11 ?: "",
            strIngredient12 = strIngredient12 ?: "",
            strIngredient13 = strIngredient13 ?: "",
            strIngredient14 = strIngredient14 ?: "",
            strIngredient15 = strIngredient15 ?: "",
            strGlass = strGlass ?: "",
            strIBA = strIBA ?: "",
            strImageAttribution = strImageAttribution ?: "",
            strImageSource = strImageSource ?: "",
            strTags = strTags ?: "",
            strVideo = strVideo ?: "",
        )
}