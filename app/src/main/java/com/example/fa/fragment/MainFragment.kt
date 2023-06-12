package com.example.fa.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fa.adapter.ActivityAdapter
import com.example.fa.adapter.WorkoutAdapter
import com.example.fa.databinding.FragmentMainBinding
import com.example.fa.model.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainFragment : Fragment() {
    private var activityList = arrayListOf<Activity>()
    private var db= Firebase.firestore

    private var qtyGlass=0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
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
        binding.btnAddWater.setOnClickListener {
            qtyGlass+=1
            binding.tvQty.text="$qtyGlass стакан"
        }

        binding.tvDateMain.setText("$dayWeek ,$day $nameMonth")
        val user = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        db.collection("User")
            .whereEqualTo("Email", user.currentUser?.email)
            .addSnapshotListener { documents: QuerySnapshot?, error ->
                if (documents != null) {
                    for (document in documents) {
                        binding.tvHeight.text = document.data.get("Height").toString()
                        binding.tvWeight.text = document.data.get("Weight").toString()
                        Glide.with(binding.root.context).load(document.data.get("url")).into(binding.imgUserMain)
                    }
                }
            }

        binding.rvActivity.layoutManager= LinearLayoutManager(binding.root.context,LinearLayoutManager.HORIZONTAL,false)
        activityList= arrayListOf()
        db.collection("Activity").whereEqualTo("UserEmail",user.currentUser?.email).get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val activity: Activity? =data.toObject(Activity::class.java)
                        if(activity!=null){
                            activityList.add(activity)
                        }
                    }
                    var adapter= ActivityAdapter(activityList)
                    binding.rvActivity.adapter=adapter
                    adapter.setOnTimeClickListener(
                        object : ActivityAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {

                            }
                        }
                    )
                }
            }
        return binding.root
    }

}