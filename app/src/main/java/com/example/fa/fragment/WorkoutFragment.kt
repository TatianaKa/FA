package com.example.fa.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fa.adapter.WorkoutAdapter
import com.example.fa.databinding.FragmentWorkoutBinding
import com.example.fa.model.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*


class WorkoutFragment : Fragment() {
    private var workoutList = arrayListOf<Workout>()
    private var db= FirebaseFirestore.getInstance()
    private val user= FirebaseAuth.getInstance()
    private lateinit var tvDate: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentWorkoutBinding.inflate(inflater)
        val c = Calendar.getInstance()
        var dayWeek=""

        val month = c.get(Calendar.MONTH)+1
        var nameMonth=""

        val day = c.get(Calendar.DAY_OF_MONTH)
        val week=c.get(Calendar.DAY_OF_WEEK)
        when(week){
            2->dayWeek="Понедельник"
            3->dayWeek="Вторник"
            4->dayWeek="Среда"
            5->dayWeek="Четверг"
            6->dayWeek="Пятница"
            7->dayWeek="Суббота"
            1->dayWeek="Воскресенье"
        }
        when(month){
            1->nameMonth="января"
            2->nameMonth="февраля"
            3->nameMonth="марта"
            4->nameMonth="апреля"
            5->nameMonth="мая"
            6->nameMonth="июня"
            7->nameMonth="июля"
            8->nameMonth="августа"
            9->nameMonth="сентября"
            10->nameMonth="октября"
            11->nameMonth="ноября"
            12->nameMonth="декабря"
        }
        binding.tvTimeWorkout.setText("$dayWeek ,$day $nameMonth")

        binding.rvWorkout.layoutManager= LinearLayoutManager(binding.root.context)

        workoutList= arrayListOf()

        db.collection("User").whereEqualTo("Email",user.currentUser?.email).
        addSnapshotListener { documents: QuerySnapshot?, error ->
            if (documents != null) {
                for (document in documents) {
                    Glide.with(binding.root.context).load(document.data.get("url"))
                        .into(binding.imgUserWorkout)
                    val tag = document.data.get("Tag").toString()
                    if (!tag.isEmpty()) {
                        db.collection("Workout").whereEqualTo("tag",tag).get()
                            .addOnSuccessListener {
                                if (!it.isEmpty) {
                                    for (data in it.documents) {
                                        val workout: Workout? = data.toObject(Workout::class.java)
                                        if (workout != null) {
                                            workoutList.add(workout)
                                        }
                                    }

                                    binding.rvWorkout.adapter = WorkoutAdapter(workoutList)
                                    var adapter = WorkoutAdapter(workoutList)
                                    binding.rvWorkout.adapter = adapter
                                    adapter.setOnTimeClickListener(
                                        object : WorkoutAdapter.onItemClickListener {
                                            override fun onItemClick(position: Int) {
                                                val activity = hashMapOf(
                                                    "UserEmail" to user.currentUser?.email,
                                                    "Date" to "$day $nameMonth",
                                                    "Duration" to workoutList[position].timeWork
                                                )
                                                db.collection("Activity").add(activity)
                                                    .addOnSuccessListener {
                                                    }
                                            }
                                        }
                                    )
                                }
                            }
                    }
                    else{
                        db.collection("Workout").get()
                            .addOnSuccessListener {
                                if (!it.isEmpty) {
                                    for (data in it.documents) {
                                        val workout: Workout? = data.toObject(Workout::class.java)
                                        if (workout != null) {
                                            workoutList.add(workout)
                                        }
                                    }

                                    binding.rvWorkout.adapter = WorkoutAdapter(workoutList)
                                    var adapter = WorkoutAdapter(workoutList)
                                    binding.rvWorkout.adapter = adapter
                                    adapter.setOnTimeClickListener(
                                        object : WorkoutAdapter.onItemClickListener {
                                            override fun onItemClick(position: Int) {
                                                val activity = hashMapOf(
                                                    "UserEmail" to user.currentUser?.email,
                                                    "Date" to "$day $nameMonth",
                                                    "Duration" to workoutList[position].timeWork
                                                )
                                                db.collection("Activity").add(activity)
                                                    .addOnSuccessListener {
                                                    }
                                            }
                                        }
                                    )
                                }
                            }
                    }
                }
            }

        }
        return binding.root
    }
}