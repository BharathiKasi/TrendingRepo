package com.example.zohotrendingrepo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo

@Dao
interface TrendingRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pTrendingRepoList:List<TrendingRepoInfo>)

    @Query("Select * from trending_repo")
    fun getAllTrendingRepoInfo():List<TrendingRepoInfo>
}