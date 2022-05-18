package com.example.zohotrendingrepo.dataclass

import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BuiltBys {
    var lBuiltByList  = mutableListOf<BuiltBy>()
    @TypeConverters
    fun setBuiltByList(pList: List<BuiltBy>):String{
        var lGson= Gson()
      return  lGson.toJson( lBuiltByList.addAll(pList))

    }
    @TypeConverters
    fun getBuiltByList(pString:String):List<BuiltBy>{
        val lType  = object : TypeToken<List<BuiltBy>>(){}.type
        var lGson = Gson()
        return lGson.fromJson<List<BuiltBy>>(pString,lType)

    }
}