package com.example.zohotrendingrepo.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.zohotrendingrepo.database.TrendingRepoRoomDb
import com.example.zohotrendingrepo.dataclass.BuiltBys
import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo
import com.example.zohotrendingrepo.retro_builder.TrendingRepoBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

const val TAG = "MainActivityRepository"
class MainActivityRepository(pContext:Context) {

    private val mIoCoroutineScope:CoroutineScope by lazy {
        CoroutineScope(Dispatchers.IO)
    }
    private val mTrendingRepoDb by lazy{
        TrendingRepoRoomDb.Companion.getDatabase(pContext)
    }

    private val mTrendingRepoMLiveData:MutableLiveData<List<TrendingRepoInfo>?>  by lazy {
        MutableLiveData<List<TrendingRepoInfo>?>()
    }
    val mTrendingRepoLiveData = mTrendingRepoMLiveData

    fun fetchTrendingRepo(){
        mIoCoroutineScope.launch {
            try {
                val lTrendingRetroService = TrendingRepoBuilder.getTrendingRepoService()
                var lQueryMap = mutableMapOf<String,String>()
                lQueryMap.put("language","")
                lQueryMap.put("since","daily")
                lQueryMap.put("spoken_language_code","")
                var lHeaderMap = mutableMapOf<String,String>()
                lHeaderMap.put("Content-Type","application/json")
                val lRetroCall= lTrendingRetroService.fetchTrendingRepo(lHeaderMap,lQueryMap)
                val lRetroResponse = lRetroCall.execute()
                if(lRetroResponse.isSuccessful){
                    Log.d(TAG,"the lretro is ${lRetroResponse.body()}")
                    val lBody = lRetroResponse.body()
                    if(lBody!=null && lBody.size>0){
                        lBody.forEach {
                            val ll  = BuiltBys()
                            ll.setBuiltByList(it.pBuiltBy)
                            it.pBuildBys = ll
                        }
                        mTrendingRepoDb.trendingRepoDao().insert(lBody)
                        mTrendingRepoMLiveData.postValue(lBody)
                    }
                }else{
                    mTrendingRepoMLiveData.postValue(null)
                    Log.d(TAG,"the error  is ${lRetroResponse.message()}")
                }
            }catch (e:Exception){
                Log.d(TAG,"the error is ${e.message}")
                e.printStackTrace()
                mTrendingRepoMLiveData.postValue(null)
            }
        }
    }
}