package com.chumachenko.core.data.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.DrinksList
import java.util.*

@Entity(tableName = "drinks_history")
data class DrinksHistory(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "dateModified") val dateModified: String,
    @ColumnInfo(name = "idDrink") val idDrink: String,
    @ColumnInfo(name = "strAlcoholic") val strAlcoholic: String,
    @ColumnInfo(name = "strCategory") val strCategory: String,
    @ColumnInfo(name = "strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: String,
    @ColumnInfo(name = "strDrink") val strDrink: String,
    @ColumnInfo(name = "strDrinkAlternate") val strDrinkAlternate: String,
    @ColumnInfo(name = "strInstructions") val strInstructions: String,
    @ColumnInfo(name = "strDrinkThumb") val strDrinkThumb: String,
    @ColumnInfo(name = "strIngredient1") val strIngredient1: String,
    @ColumnInfo(name = "strIngredient2") val strIngredient2: String,
    @ColumnInfo(name = "strIngredient3") val strIngredient3: String,
    @ColumnInfo(name = "strIngredient4") val strIngredient4: String,
    @ColumnInfo(name = "strIngredient5") val strIngredient5: String,
    @ColumnInfo(name = "strIngredient6") val strIngredient6: String,
    @ColumnInfo(name = "strGlass") val strGlass: String,
    @ColumnInfo(name = "strIBA") val strIBA: String,
    @ColumnInfo(name = "strImageAttribution") val strImageAttribution: String,
    @ColumnInfo(name = "strImageSource") val strImageSource: String,
    @ColumnInfo(name = "strTags") val strTags: String,
    @ColumnInfo(name = "strVideo") val strVideo: String,
    @ColumnInfo(name = "saved_at") val savedAt: Long = System.currentTimeMillis()
) {
    fun toDrinkModel() =
        Drink(
            dateModified,
            idDrink,
            strAlcoholic,
            strCategory,
            strCreativeCommonsConfirmed,
            strDrink,
            strDrinkAlternate,
            strInstructions,
            strDrinkThumb,
            strIngredient1,
            strIngredient2,
            strIngredient3,
            strIngredient4,
            strIngredient5,
            strIngredient6,
            strGlass,
            strIBA,
            strImageAttribution,
            strImageSource,
            strTags,
            strVideo
        )
}