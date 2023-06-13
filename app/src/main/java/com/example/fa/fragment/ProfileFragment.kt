package com.example.fa.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fa.R
import com.example.fa.adapter.AchievementAdapter
import com.example.fa.adapter.ActivityAdapter
import com.example.fa.auth.LoginActivity
import com.example.fa.databinding.FragmentProfileBinding
import com.example.fa.model.Achievement
import com.example.fa.model.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private var activityList = arrayListOf<Activity>()
    private var achievementList = arrayListOf<Achievement>()
    private var db= FirebaseFirestore.getInstance()
    private var user=FirebaseAuth.getInstance().currentUser!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding=FragmentProfileBinding.inflate(layoutInflater)
        binding.tvExit.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(binding.root.context,LoginActivity::class.java))
        }
        db.collection("User").whereEqualTo("Email",user.email).
        addSnapshotListener { documents: QuerySnapshot?, error ->
            if (documents != null) {
                for (document in documents) {
                    Glide.with(binding.root.context).load(document.data.get("url"))
                        .into(binding.imgUserProfile)
                    binding.tvName.text = document.data.get("Name").toString()
                }
            }
        }
        binding.rvActivityMain.layoutManager= LinearLayoutManager(binding.root.context,LinearLayoutManager.HORIZONTAL,false)
        binding.rvAchievement.layoutManager= LinearLayoutManager(binding.root.context)
        activityList= arrayListOf()
        achievementList= arrayListOf()
        db.collection("Activity").whereEqualTo("UserEmail",user.email).get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val activity: Activity? =data.toObject(Activity::class.java)
                        if(activity!=null){
                            activityList.add(activity)
                        }
                    }
                    var adapter= ActivityAdapter(activityList)
                    binding.rvActivityMain.adapter=adapter
                    adapter.setOnTimeClickListener(
                        object : ActivityAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {

                            }
                        }
                    )
                }
            }
        db.collection("Achievement").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val achievement: Achievement? =data.toObject(Achievement::class.java)
                        if(achievement!=null){
                            achievementList.add(achievement)
                        }
                    }
                    var adapter= AchievementAdapter(achievementList)
                    binding.rvAchievement.adapter=adapter
                    adapter.setOnTimeClickListener(
                        object : AchievementAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {

                            }
                        }
                    )
                }
            }

        return binding.root
    }
}