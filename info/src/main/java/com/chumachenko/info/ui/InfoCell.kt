package com.chumachenko.info.ui

import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult

sealed class InfoCell {

    data class Item(
        val drink: Drink,
        val ingredients: List<String>
    ) : InfoCell()

    object Shimmer : InfoCell()
}