package com.example.fa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.fa.R
import com.example.fa.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private var db= Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentProfileBinding.inflate(inflater)
        val user= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        db.collection("User").whereEqualTo("Email",user.currentUser?.email).
        addSnapshotListener { documents: QuerySnapshot?, error ->
            if (documents != null) {
                for (document in documents) {
                    Glide.with(binding.root.context).load(document.data.get("url"))
                        .into(binding.imgUserProfile)
                    binding.tvName.text = document.data.get("Name").toString()
                }
            }
        }
        return binding.root
    }
}