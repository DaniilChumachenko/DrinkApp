package com.chumachenko.search.ui

import com.chumachenko.core.data.model.Drink


sealed class SearchCell {

    data class Item(
        val item: Drink,
        val selectId: String = "",
        val ingredients: List<String> = listOf(),
    ) : SearchCell()

    object Empty : SearchCell()

    object Search : SearchCell()

    object Skeleton : SearchCell()
}