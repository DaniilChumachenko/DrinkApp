package com.chumachenko.core.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class NetworkUtils(private val context: Context) {

    fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        if (activeNetwork != null) {
            val nc = cm.getNetworkCapabilities(activeNetwork)
            if (nc != null) {
                return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            }
        }
        return false
    }

}