package com.example.zohotrendingrepo.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrendingRepoInfo(
    @SerializedName("author") val pAuthor:String,
    @SerializedName("name") val pName:String,
    @SerializedName("avatar") val pAvatar:String,
    @SerializedName("url") val pRepoUrl:String,
    @SerializedName("description") val pDescription:String,
    @SerializedName("language") val pLanguage:String,
    @SerializedName("languageColor") val pLanguageColor:String,
    @SerializedName("stars") val pStars:Long,
    @SerializedName("forks") val pForks:Long,
    @SerializedName("currentPeriodStars") val pCurrentPeriodStars:Long,
    @SerializedName("builtBy") val pBuiltBy: List<BuiltBy>,
    @SerializedName("builtBys") var pBuildBys:BuiltBys
): Parcelable

@Parcelize
data class BuiltBy(@SerializedName("href") val pHref:String,
                   @SerializedName("avatar") val pAvatar:String,
                   @SerializedName("username") val pUserName:String
                   ): Parcelable
