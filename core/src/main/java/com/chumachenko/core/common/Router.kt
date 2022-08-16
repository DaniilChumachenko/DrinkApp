package com.chumachenko.core.common

import androidx.fragment.app.FragmentManager
import com.chumachenko.core.data.model.Drink

interface Router {

    fun openInfoScreen(
        fragmentManager: FragmentManager,
        drink: Drink
    )

}