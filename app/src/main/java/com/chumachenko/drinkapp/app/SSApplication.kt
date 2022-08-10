package com.chumachenko.drinkapp.app

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chumachenko.core.common.Router

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

    override fun openProfileFullscreen(
        fragmentManager: FragmentManager,
        userId: Int,
        userName: String
    ) {
//        replaceFragment(
//            R.id.container,
//            fragmentManager,
//            ProfileFragment.newInstance(userId, true, userName),
//            FragmentTransaction.TRANSIT_FRAGMENT_FADE
//        )
    }

    override fun openProfileByUserName(fragmentManager: FragmentManager, userName: String) {
//        replaceFragment(
//            R.id.container,
//            fragmentManager,
//            ProfileFragment.newInstanceByUserName(userName, false),
//            FragmentTransaction.TRANSIT_FRAGMENT_FADE
//        )
    }

    override fun openAsk(fragmentManager: FragmentManager, userId: Int) {
//        replaceFragment(
//            R.id.container,
//            fragmentManager,
//            AskItemsFragment.newInstance(userId),
//            FragmentTransaction.TRANSIT_FRAGMENT_FADE
//        )
    }

    override fun openWallet(
        fragmentManager: FragmentManager,
        userName: String,
        userId: Int,
        month: String
    ) {
//        replaceFragment(
//            R.id.container,
//            fragmentManager,
//            WalletFragment.newInstance(userName, userId, month),
//            FragmentTransaction.TRANSIT_FRAGMENT_FADE
//        )
    }
}