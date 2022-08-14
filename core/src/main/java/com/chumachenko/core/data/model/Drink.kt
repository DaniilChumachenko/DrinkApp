package com.chumachenko.core.data.model

import android.os.Parcelable
import com.chumachenko.core.data.storage.database.entity.DrinksHistory
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(
    val dateModified: String,
    val idDrink: String,
    val strAlcoholic: String,
    val strCategory: String,
    val strCreativeCommonsConfirmed: String,
    val strDrink: String,
    val strDrinkAlternate: String,
    val strInstructions: String,
    val strDrinkThumb: String,
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strGlass: String,
    val strIBA: String,
    val strImageAttribution: String,
    val strImageSource: String,
    val strTags: String,
    val strVideo: String
) : Parcelable {

    fun toEntity(): DrinksHistory = DrinksHistory(
        dateModified = dateModified,
        idDrink = idDrink,
        strAlcoholic = strAlcoholic,
        strCategory = strCategory,
        strCreativeCommonsConfirmed = strCreativeCommonsConfirmed,
        strDrink = strDrink,
        strDrinkAlternate = strDrinkAlternate,
        strInstructions = strInstructions,
        strDrinkThumb = strDrinkThumb,
        strIngredient1 = strIngredient1,
        strIngredient2 = strIngredient2,
        strIngredient3 = strIngredient3,
        strIngredient4 = strIngredient4,
        strIngredient5 = strIngredient5,
        strIngredient6 = strIngredient6,
        strGlass = strGlass,
        strIBA = strIBA,
        strImageAttribution = strImageAttribution,
        strImageSource = strImageSource,
        strTags = strTags,
        strVideo = strVideo
    )
}