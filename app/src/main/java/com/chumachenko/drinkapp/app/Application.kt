package com.chumachenko.drinkapp.app

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chumachenko.core.common.Router
import com.chumachenko.drinkapp.R

class Application : Application(), Router {

    private fun replaceFragment(
        containerViewId: Int,
        fragmentManager: FragmentManager,
        fragment: Fragment,
        transition: Int
    ) {
        val transaction = fragmentManager
            .beginTransaction()
            .replace(
                containerViewId,
                fragment,
                fragment.javaClass.simpleName
            )
            .addToBackStack(fragment.javaClass.simpleName)
            .setTransition(transition)

        if (!fragmentManager.isStateSaved) {
            transaction.commit()
        }
    }

    private fun addFragment(
        containerViewId: Int,
        fragmentManager: FragmentManager,
        fragment: Fragment,
        transition: Int
    ) {
        val transaction = fragmentManager
            .beginTransaction()
            .add(
                containerViewId,
                fragment,
                fragment.javaClass.simpleName
            )
            .addToBackStack(fragment.javaClass.simpleName)
            .setTransition(transition)

        if (!fragmentManager.isStateSaved) {
            transaction.commit()
        }
    }

    override fun openSearchScreen(fragmentManager: FragmentManager) {

    }
}