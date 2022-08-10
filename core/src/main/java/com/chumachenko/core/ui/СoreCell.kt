package com.chumachenko.core.ui

import com.chumachenko.core.data.model.Drink

sealed class СoreCell {

    data class Item(
        val item: Drink,
        val selectId: String = "",
        val ingredients: List<String> = listOf(),
    ) : СoreCell()

    object Empty : СoreCell()

    object Search : СoreCell()

    object Skeleton : СoreCell()
}