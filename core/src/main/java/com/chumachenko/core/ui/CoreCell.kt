package com.chumachenko.core.ui

import com.chumachenko.core.data.model.Drink
import com.chumachenko.core.data.model.SearchResult

sealed class CoreCell {

    data class Item(
        val item: Drink,
        val ingredients: List<String> = listOf(),
    ) : CoreCell()

    data class Recent(
        val item: List<SearchResult>
    ) : CoreCell()

    object Start : CoreCell()

    object Empty : CoreCell()

    object Space : CoreCell()

    object Shimmer : CoreCell()
}