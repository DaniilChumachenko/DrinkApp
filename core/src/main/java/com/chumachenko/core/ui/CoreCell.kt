package com.chumachenko.core.ui

import com.chumachenko.core.data.model.Drink

sealed class CoreCell {

    data class Item(
        val item: Drink,
        val selectId: String = "",
        val ingredients: List<String> = listOf(),
    ) : CoreCell()

    object Start : CoreCell()

    object Empty : CoreCell()

    object Space : CoreCell()

    object Skeleton : CoreCell()
}