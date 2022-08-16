package com.chumachenko.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullDrinksList(
    val drinks: List<Drink>
) : Parcelable