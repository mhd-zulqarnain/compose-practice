package com.project.tailor.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler
@Inject constructor(@ApplicationContext private val context: Context) {
    val isConnected get() = context.networkAvailable
}

val Context.networkAvailable: Boolean
    get() = isNetworkAvailable(this)

private fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager?.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device who are able to connect with Ethernet
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            //for check internet over VPN
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }
    } else {
        return connectivityManager?.activeNetworkInfo?.isConnected ?: false
    }
}
