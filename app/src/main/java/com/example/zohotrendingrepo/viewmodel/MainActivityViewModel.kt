package com.example.zohotrendingrepo.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo
import com.example.zohotrendingrepo.helper.NetworkInfo
import com.example.zohotrendingrepo.repository.MainActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(private val pApplication: Application,private val pCoroutineContext: CoroutineContext,    private val pLifeCycleOwner: LifecycleOwner
) : AndroidViewModel(pApplication) {
    private  val mNetWorkInfo:NetworkInfo by lazy {
        NetworkInfo(pApplication)
    }
    private val mMainActivityReposi:MainActivityRepository by lazy {
        MainActivityRepository(pApplication)
    }

    private val mNetworkAvailabilityMLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val mTrendingRepoListMLiveDta by lazy {
        MutableLiveData<List<TrendingRepoInfo>>()
    }
    val mTrendingRepoListLiveDta = mTrendingRepoListMLiveDta
    val mNetworkAvailabilityLiveData = mNetworkAvailabilityMLiveData
    private var mIsNetWorkAvailable = false
    init {
        observeNetwork()
        obserTrendingRepo()
    }


    fun fetchTrendingRepo(){
        viewModelScope.launch (pCoroutineContext){
            if(mIsNetWorkAvailable){
                mNetworkAvailabilityMLiveData.value = true
                mMainActivityReposi.fetchTrendingRepo()
            }else{
                mNetworkAvailabilityMLiveData.value = false
            }
        }
    }

    private fun observeNetwork(){
        mNetWorkInfo.observe(pLifeCycleOwner, Observer {
            mIsNetWorkAvailable =it
            if(mIsNetWorkAvailable){
                fetchTrendingRepo()
            }
        })
    }

    private fun obserTrendingRepo(){
        mMainActivityReposi.mTrendingRepoLiveData.observe(pLifeCycleOwner, Observer { pTrendingRopList ->
            if(pTrendingRopList!=null && pTrendingRopList.isNotEmpty()){
                mTrendingRepoListMLiveDta.postValue(pTrendingRopList)
            }else{
                mTrendingRepoListMLiveDta.postValue(emptyList())
            }
        })
    }

}