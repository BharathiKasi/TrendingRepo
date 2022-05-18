package com.example.zohotrendingrepo.retro_service

import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface TrendingRetroService {

    @GET("repositories")
    fun fetchTrendingRepo(@HeaderMap pHeaderMap:Map<String,String>,@QueryMap pQueryMap:Map<String,String>):Call<List<TrendingRepoInfo>>
}