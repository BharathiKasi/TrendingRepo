package com.example.zohotrendingrepo.type_converter

import androidx.room.TypeConverter
import com.example.zohotrendingrepo.dataclass.BuiltBy
import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object BuiltByTypeConverters {

    @TypeConverter
    fun convertListToString(pList:List<BuiltBy>):String{
        val lGson = Gson()
        return lGson.toJson(pList)
    }

    @TypeConverter
    fun convertStringToList(pStringh:String):List<TrendingRepoInfo>{
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(pStringh,listType)
    }
}