package com.example.fa.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.fa.R
import com.example.fa.auth.DataRegActivity
import com.example.fa.model.Activity
import com.example.fa.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class EditDataActivity : AppCompatActivity() {
    private lateinit var tvEdit:TextView
    private lateinit var etEdit:EditText
    private lateinit var btnEdit:Button
    private val db=FirebaseFirestore.getInstance()
    private val users=FirebaseAuth.getInstance().currentUser!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)
        supportActionBar?.hide();
        tvEdit=findViewById(R.id.tvEdit)
        etEdit=findViewById(R.id.etEdit)
        btnEdit=findViewById(R.id.btnEdit)
        val type=intent.getStringExtra("type")
        if(type=="Height"){
            tvEdit.setText("Изменить рост")
            btnEdit.setOnClickListener{
                update("Height",etEdit.text.toString())
            }
        }
        else if(type=="Weight"){
            tvEdit.setText("Изменить вес")
            btnEdit.setOnClickListener{
                update("Weight",etEdit.text.toString())
            }

        }

    }
    fun update(type:String,value:String){
        db.collection("User").whereEqualTo("Email",users.email).get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        if(type=="Weight"){
                            db.collection("User").document(data.id).update("Weight",value)
                        }
                        else if(type=="Height"){
                            db.collection("User").document(data.id).update("Height",value)
                        }

                    }
                }
            }
    }

}