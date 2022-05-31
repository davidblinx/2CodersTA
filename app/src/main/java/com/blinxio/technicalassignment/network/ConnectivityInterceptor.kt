package com.blinxio.technicalassignment.network

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(private val connectivityManager: ConnectivityManager) :
    Interceptor {

    private val capabilities
        get() =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    private val activeNetwork get() = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!activeNetwork) throw IOException("No internet connection available")

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}