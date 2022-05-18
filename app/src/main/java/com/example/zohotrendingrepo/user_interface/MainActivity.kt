package com.example.zohotrendingrepo.user_interface

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zohotrendingrepo.R
import com.example.zohotrendingrepo.adapter.TrendingRepAdapter
import com.example.zohotrendingrepo.database.TrendingRepoRoomDb
import com.example.zohotrendingrepo.databinding.ActivityMainBinding
import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo
import com.example.zohotrendingrepo.factory.MainActivityFactory
import com.example.zohotrendingrepo.viewmodel.MainActivityViewModel

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var mMainActivityBinding:ActivityMainBinding
    private lateinit var mMainActivityViewModel: MainActivityViewModel
    private lateinit var mProgressDialog:Dialog
    private lateinit var mTrendingRepoAdapter:TrendingRepAdapter
    private  var mActualList = mutableListOf<TrendingRepoInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val lFactory = MainActivityFactory(this,application,this)
        mMainActivityViewModel = ViewModelProvider(this,lFactory).get(MainActivityViewModel::class.java)
        mMainActivityViewModel.mNetworkAvailabilityLiveData.observe(this, Observer {
            if(!it){
                Toast.makeText(this,"No network available",Toast.LENGTH_LONG).show()
                swithProgressBarVisibillity(false)
                mMainActivityBinding.mSwipeToRefresh.isRefreshing= false
            }else{
                val ll = TrendingRepoRoomDb.Companion.getDatabase(this).trendingRepoDao().getAllTrendingRepoInfo()
                if(!mMainActivityBinding.mSwipeToRefresh.isRefreshing){
                    swithProgressBarVisibillity(true)
                }
            }
        })
        mMainActivityBinding.mSwipeToRefresh.setColorSchemeColors(ContextCompat.getColor(this,R.color.white))
        mMainActivityBinding.mSwipeToRefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this,R.color.blue))

        mMainActivityBinding.mSwipeToRefresh.setOnRefreshListener {
            Log.d(TAG,"the refresh is called...")
            mMainActivityViewModel.fetchTrendingRepo()
        }
        mMainActivityViewModel.mTrendingRepoListLiveDta.observe(this, Observer {
            Log.d(TAG,"the list size is ${it.size}")
            swithProgressBarVisibillity(false)
            mMainActivityBinding.RecyclerView.visibility = View.VISIBLE
            mMainActivityBinding.SearchIconIV.visibility = View.VISIBLE
            mMainActivityBinding.mSwipeToRefresh.isRefreshing= false
            mActualList.clear()
            mActualList.addAll(it)
            if(it.isNotEmpty()){
                updateAdapter(it)
            }
        })
        mMainActivityBinding.SearchIconIV.setOnClickListener {
            mMainActivityBinding.SearchIconParent.visibility = View.GONE
            mMainActivityBinding.SearchingParent.visibility = View.VISIBLE
            changeKeyboardVisibility(pIsShow = true)
        }
        mMainActivityBinding.CancelIv.setOnClickListener {
            //mMainActivityBinding.SearchingParent.visibility = View.GONE
            //mMainActivityBinding.SearchIconParent.visibility = View.VISIBLE
            mMainActivityBinding.EditText.setText("")
            //changeKeyboardVisibility(false)
        }
        mMainActivityBinding.BackButton.setOnClickListener {
            mMainActivityBinding.SearchIconParent.visibility = View.VISIBLE
            mMainActivityBinding.SearchingParent.visibility = View.GONE
            mMainActivityBinding.EditText.setText("")
            changeKeyboardVisibility(pIsShow = false)
        }
        mMainActivityBinding.EditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }

        })
    }

    private fun changeKeyboardVisibility(pIsShow:Boolean){
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if(pIsShow){
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
        }else{
            inputMethodManager.hideSoftInputFromWindow(mMainActivityBinding.root.windowToken,0)

        }
    }

    private fun updateAdapter(pList:List<TrendingRepoInfo>){
        if(!this::mTrendingRepoAdapter.isInitialized){
            mTrendingRepoAdapter = TrendingRepAdapter(this)
            mMainActivityBinding.RecyclerView.layoutManager = LinearLayoutManager(this)
            mMainActivityBinding.RecyclerView.adapter = mTrendingRepoAdapter
        }
        mTrendingRepoAdapter.updateList(pList)
    }

    private fun swithProgressBarVisibillity(pIsShow:Boolean){
            if(pIsShow){
                mMainActivityBinding.ProgressBar.visibility = View.VISIBLE
                mMainActivityBinding.ProgressBar.isIndeterminate = true
            }else{
                mMainActivityBinding.ProgressBar.visibility = View.GONE
            }

    }

    private fun filterList(pString: String){
        if(pString.length>0){
            var lList = mutableListOf<TrendingRepoInfo>()
            mActualList.forEach {
                if(it.pAuthor.contains(pString,ignoreCase = true)|| it.pDescription.contains(pString,ignoreCase = true) || it.pName.contains(pString,ignoreCase = true)|| it.pLanguage.contains(pString,ignoreCase = true)){
                    lList.add(it)
                }
            }
            updateAdapter(pList = lList)
        }else{
            updateAdapter(pList = mActualList)
        }
    }
}