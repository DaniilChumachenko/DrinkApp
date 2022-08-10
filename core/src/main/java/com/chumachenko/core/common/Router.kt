package com.chumachenko.core.common

import androidx.fragment.app.FragmentManager

interface Router {

    fun openProfileFullscreen(
        fragmentManager: FragmentManager,
        userId: Int, userName: String
    )

    fun openProfileByUserName(
        fragmentManager: FragmentManager,
        userName: String
    )

    fun openAsk(
        fragmentManager: FragmentManager,
        userId: Int
    )

    fun openWallet(
        fragmentManager: FragmentManager,
        userName: String,
        userId: Int,
        month: String
    )

}