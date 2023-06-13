package com.example.fa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fa.R
import com.example.fa.model.Achievement
import com.example.fa.model.Activity
import java.util.ArrayList

class AchievementAdapter(private val achievementList: ArrayList<Achievement>): RecyclerView.Adapter<AchievementAdapter.AchievementHolder>(){
    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnTimeClickListener(listener:onItemClickListener){
        mListener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_achievement,parent,false)
        return AchievementHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return achievementList.size
    }

    override fun onBindViewHolder(holder: AchievementHolder, position: Int) {
        val currentItem=achievementList[position]
        holder.tvAchievement.text=currentItem.Name
    }

    class AchievementHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val tvAchievement: TextView =itemView.findViewById(R.id.tvAchievement)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}