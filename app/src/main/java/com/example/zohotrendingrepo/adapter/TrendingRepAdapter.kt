package com.example.zohotrendingrepo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zohotrendingrepo.R
import com.example.zohotrendingrepo.dataclass.TrendingRepoInfo
import com.google.android.material.imageview.ShapeableImageView

class TrendingRepAdapter(private val pContext: Context) : RecyclerView.Adapter<TrendingRepAdapter.TrendingRepoViewHolder>() {
    private  var mTrendingRepoInfoList : List<TrendingRepoInfo> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingRepoViewHolder {
        val lView = LayoutInflater.from(parent.context).inflate(R.layout.trending_repo_list,parent,false)
        return TrendingRepoViewHolder(lView)
    }

    fun updateList(pList:List<TrendingRepoInfo>){
       val oldList = mTrendingRepoInfoList
        val lDiffResult = DiffUtil.calculateDiff(DiffUtilCallBack(oldList,pList))
        mTrendingRepoInfoList = pList
        lDiffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
       return mTrendingRepoInfoList.size
    }

    override fun onBindViewHolder(holder: TrendingRepoViewHolder, position: Int) {
        val lTrendingRepoInfo = mTrendingRepoInfoList[position]
        Glide.with(pContext).load(lTrendingRepoInfo.pAvatar).into(holder.lProfileUrlIv)
        holder.lAuthorTv.text = "${lTrendingRepoInfo.pAuthor}"
        holder.lDescriptionTv.text = " ${lTrendingRepoInfo.pDescription} "
        val ll =  lTrendingRepoInfo.pLanguage
        holder.lLanguageTv.text =ll
        holder.lNameTv.text = "${lTrendingRepoInfo.pName}"
        holder.lStarsTv.text = "${lTrendingRepoInfo.pStars}"
        holder.lLanguageIv.setColorFilter(Color.parseColor(lTrendingRepoInfo.pLanguageColor))

    }


     class TrendingRepoViewHolder( pView:View): RecyclerView.ViewHolder(pView){
        val lProfileUrlIv  = pView.findViewById<ShapeableImageView>(R.id.ProfileIv)
         val lAuthorTv = pView.findViewById<AppCompatTextView>(R.id.AuthorTv)
         val lNameTv = pView.findViewById<AppCompatTextView>(R.id.NameTv)
         val lDescriptionTv =  pView.findViewById<AppCompatTextView>(R.id.DescriptionTv)
         val lStarsTv = pView.findViewById<AppCompatTextView>(R.id.StarsTv)
         val lLanguageTv  = pView.findViewById<AppCompatTextView>(R.id.LanguageTv)
         val lLanguageIv = pView.findViewById<AppCompatImageView>(R.id.LanguageColorIv)
    }

     class DiffUtilCallBack(var pOldList: List<TrendingRepoInfo>,var pNewList: List<TrendingRepoInfo>) : DiffUtil.Callback(){
         override fun getOldListSize(): Int {
             return pOldList.size
         }

         override fun getNewListSize(): Int {
             return pNewList.size
         }

         override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
             return pOldList[oldItemPosition] == pNewList[newItemPosition]
         }


         override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
             return pOldList[oldItemPosition].pAuthor == pNewList[newItemPosition].pAuthor

         }
    }
}