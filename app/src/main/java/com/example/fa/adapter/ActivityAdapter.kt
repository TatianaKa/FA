package com.example.fa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fa.R
import com.example.fa.model.Activity
import com.example.fa.model.Workout
import java.util.ArrayList

class ActivityAdapter(private val activityList: ArrayList<Activity>): RecyclerView.Adapter<ActivityAdapter.ActivityHolder>(){
    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnTimeClickListener(listener:onItemClickListener){
        mListener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_activity,parent,false)
        return ActivityHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        val currentItem=activityList[position]
        holder.tvDurationActivity.text=currentItem.Duration
        holder.tvDateActivity.text=currentItem.Date
    }

    class ActivityHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val tvDurationActivity:TextView=itemView.findViewById(R.id.tvDurationActivity)
        val tvDateActivity:TextView=itemView.findViewById(R.id.tvDateActivity)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}