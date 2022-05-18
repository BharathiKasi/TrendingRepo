package com.example.zohotrendingrepo.helper

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class NetworkInfo(private val pContext:Context): LiveData<Boolean>(){
    private lateinit var mNetworkCallback: ConnectivityManager.NetworkCallback
    private val mConnectivityManager:ConnectivityManager = pContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private var mNetworkMSet = mutableSetOf<Network>()
    override fun onActive() {
        super.onActive()
        mNetworkCallback = createNetworkCallBack()
        val lNetWorkRequest =  NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        mConnectivityManager.registerNetworkCallback(lNetWorkRequest,mNetworkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        mConnectivityManager.unregisterNetworkCallback(mNetworkCallback)
    }
    private fun checkAllNetworkAvailability(){
       postValue(mNetworkMSet.size>0)
    }

    private fun createNetworkCallBack() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val lNetworkCapabilities = mConnectivityManager.getNetworkCapabilities(network)
            val lIsNetworkAvailable = lNetworkCapabilities!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if(lIsNetworkAvailable){
                mNetworkMSet.add(network)
            }
            checkAllNetworkAvailability()
            //postValue(lIsNetworkAvailable)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            mNetworkMSet.remove(network)
            checkAllNetworkAvailability()
        }

    }
}