package com.chumachenko.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinksList(
    val drinks: List<Drink>
) : Parcelable