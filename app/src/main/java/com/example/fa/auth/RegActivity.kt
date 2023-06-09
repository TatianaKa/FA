package com.example.fa.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RegActivity : AppCompatActivity() {
    private lateinit var btnRegImage: Button
    private lateinit var etRegName: EditText
    private lateinit var etRegEmail: EditText
    private lateinit var etRegPass: EditText
    private lateinit var etRegConfPass: EditText
    private lateinit var btnReg: Button
    private lateinit var tvAuth: TextView
    private lateinit var img: ImageView

    private var storageRef= Firebase.storage
    private lateinit var uri: Uri
    private var isImage:Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        btnRegImage=findViewById(R.id.btnRegImage)
        img=findViewById(R.id.img)
        val galleryImage=registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                img.setImageURI(it)
                if (it != null) {
                    uri=it
                    isImage=true
                }
            }
        )

        btnRegImage.setOnClickListener {
            galleryImage.launch("image/*")
        }

        etRegName=findViewById(R.id.etRegName)
        etRegEmail=findViewById(R.id.etRegEmail)
        etRegPass=findViewById(R.id.etRegPass)
        etRegConfPass=findViewById(R.id.etRegConfPass)

        btnReg=findViewById(R.id.btnReg)
        btnReg.setOnClickListener { SaveData() }

        tvAuth=findViewById(R.id.tvAuth)
        tvAuth.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }


    }
    fun SaveData() {
        val db = FirebaseFirestore.getInstance()

        var name = etRegName.text.toString()
        var email = etRegEmail.text.toString()
        var pass = etRegPass.text.toString()
        var confPass = etRegConfPass.text.toString()

        if (email.isEmpty() || pass.isEmpty() || confPass.isEmpty() || name.isEmpty()) {
            // Toast.makeText(this, "Пустое значение. Пожалуйста введите все данные", Toast.LENGTH_SHORT).show()
        } else if (pass != confPass) {
            // Toast.makeText(this, "Не совпадают пароли", Toast.LENGTH_SHORT).show()
        } else {
            val auth= FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener {
                //tvError.setText("Добавлено")
            }
            storageRef.getReference("images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val user = hashMapOf(
                                "Name" to name,
                                "Email" to email,
                                "Pass" to pass,
                                "url" to it.toString()
                            )
                            db.collection("User")
                                .add(user)
                                .addOnSuccessListener { documentReference ->

                                    val intent = Intent(this, DataRegActivity::class.java)
                                    intent.putExtra("path", documentReference.id)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->

                                }
                        }
                }

        }

    }
}