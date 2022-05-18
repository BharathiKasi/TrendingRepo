package com.example.zohotrendingrepo.factory

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zohotrendingrepo.viewmodel.MainActivityViewModel
import kotlinx.coroutines.Dispatchers

class MainActivityFactory(private val pContext: Context,private val pApplication:Application,private val pLifeCycleOwner: LifecycleOwner
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(pApplication,Dispatchers.Main,pLifeCycleOwner) as T
        }
        throw ClassNotFoundException("MainActivityViewModel is not found.")

    }

}