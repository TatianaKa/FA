package com.example.fa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fa.R
import com.example.fa.model.Activity
import com.example.fa.model.Workout
import kotlin.collections.ArrayList

class WorkoutAdapter(private val workoutList: ArrayList<Workout>): RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder>(){
    private lateinit var mListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnTimeClickListener(listener:onItemClickListener){
        mListener=listener

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return WorkoutHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        val currentItem=workoutList[position]
        holder.tvNameWorkout.text=currentItem.name
        holder.tvDescWorkout.text=currentItem.description
        holder.tvTimeWorkout.text=currentItem.timeWork
    }

    class WorkoutHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val tvNameWorkout: TextView =itemView.findViewById(R.id.rvNameWorkout)
        val tvDescWorkout: TextView =itemView.findViewById(R.id.rvDescWorkout)
        val tvTimeWorkout: TextView =itemView.findViewById(R.id.rvTimeWorkout)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

}