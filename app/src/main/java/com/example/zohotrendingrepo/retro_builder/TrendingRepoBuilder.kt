package com.example.zohotrendingrepo.retro_builder

import com.example.zohotrendingrepo.retro_service.TrendingRetroService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TrendingRepoBuilder {

    private val lBaseUrl = "https://private-anon-d8e5e309bd-githubtrendingapi.apiary-mock.com/"
    private val mClient = OkHttpClient.Builder().build()
    private val mGson:GsonConverterFactory = GsonConverterFactory.create()

    private val mRetroBuild = Retrofit.Builder().baseUrl(lBaseUrl).client(mClient).addConverterFactory(mGson).build()


    fun getTrendingRepoService():TrendingRetroService{
        return mRetroBuild.create(TrendingRetroService::class.java)
    }
}