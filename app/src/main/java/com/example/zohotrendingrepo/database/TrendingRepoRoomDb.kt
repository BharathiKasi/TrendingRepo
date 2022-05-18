package com.example.zohotrendingrepo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zohotrendingrepo.dao.TrendingRepoDao
import com.example.zohotrendingrepo.dataclass.room_dataclass.TrendingRepo
import com.example.zohotrendingrepo.type_converter.BuiltByTypeConverters

@Database(entities = arrayOf(TrendingRepo::class), exportSchema = false, version = 1)
@TypeConverters(BuiltByTypeConverters::class)
abstract class TrendingRepoRoomDb : RoomDatabase() {

    abstract fun trendingRepoDao():TrendingRepoDao

    companion object{
        var INSTANCE:TrendingRepoRoomDb?= null
        private val DB_NAME = "TrendingRepoRoomDb.db"

        fun getDatabase(pContext:Context):TrendingRepoRoomDb{
            if(INSTANCE==null){
                synchronized(this){
                    if(INSTANCE==null){
                        INSTANCE = Room.databaseBuilder(pContext,TrendingRepoRoomDb::class.java,
                            DB_NAME).build()
                    }
                }
            }
            return  INSTANCE!!
        }
    }




}