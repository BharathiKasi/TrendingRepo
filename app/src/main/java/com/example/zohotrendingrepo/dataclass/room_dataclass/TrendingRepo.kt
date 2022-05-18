package com.example.zohotrendingrepo.dataclass.room_dataclass

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.zohotrendingrepo.dataclass.BuiltBy
import com.example.zohotrendingrepo.dataclass.BuiltBys
import com.example.zohotrendingrepo.type_converter.BuiltByTypeConverters

@androidx.room.Entity(tableName = "trending_repo")
data class TrendingRepo(
    @PrimaryKey
    @ColumnInfo(name = "author")
    val author:String,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "avatar")
    val avatar:String,
    @ColumnInfo(name = "url")
    val url:String,
    @ColumnInfo(name = "description")
    val description:String,
    @ColumnInfo(name = "language")
    val language:String,
    @ColumnInfo(name = "languageColor")
    val languageColor:String,
    @ColumnInfo(name = "stars")
    val stars:Long,
    @ColumnInfo(name = "forks")
    val forks:Long,
    @ColumnInfo(name = "currentPeriodStars")
    val currentPeriodStars:Long,
    @ColumnInfo(name = "builtBy")
    @TypeConverters(BuiltBys::class)
    val builtBy:BuiltBys?=null
    )
