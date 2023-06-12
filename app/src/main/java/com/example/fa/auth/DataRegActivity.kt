package com.example.fa.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.fa.R
import com.example.fa.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore

class DataRegActivity : AppCompatActivity() {
    private lateinit var etRegWeight: EditText
    private lateinit var etRegHeight: EditText
    private lateinit var etRegAge: EditText
    private lateinit var spGender: Spinner
    private lateinit var spTag: Spinner
    private lateinit var btnRegData: Button
    private lateinit var tvBackFirstReg: TextView
    private lateinit var genderName:String
    private lateinit var tagName:String
    val gender = arrayOf("Мужской","Женский")
    val tag = arrayOf("набор мышечной массы","похудение","поддержание веса")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_reg)

        supportActionBar?.hide();
        etRegWeight=findViewById(R.id.etRegWeight)
        etRegHeight=findViewById(R.id.etRegHeight)
        etRegAge=findViewById(R.id.etRegAge)

        spGender=findViewById(R.id.spGender)
        spTag=findViewById(R.id.spTag)
        btnRegData=findViewById(R.id.btnRegData)
        btnRegData.setOnClickListener { SaveData() }
        tvBackFirstReg=findViewById(R.id.tvBackFirstReg)

        val genderAdapter=
            ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,gender)
        spGender.adapter=genderAdapter
        spGender.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                genderName=gender[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        val tagAdapter= ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tag)
        spTag.adapter=tagAdapter
        spTag.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tagName=tag[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    fun SaveData() {
        val db = FirebaseFirestore.getInstance()

        var weight = etRegWeight.text.toString()
        var height = etRegHeight.text.toString()
        var age = etRegAge.text.toString()
        val path = intent.getStringExtra("path").toString()
        if (weight.isEmpty() || height.isEmpty() || age.isEmpty()) {
            // Toast.makeText(this, "Пустое значение. Пожалуйста введите все данные", Toast.LENGTH_SHORT).show()
        }
        else {
            val user = hashMapOf(
                "Weight" to weight,
                "Height" to height,
                "Age" to age
            )
            db.collection("User").document(path).update("Weight",weight,
                "Height",height,
                "Age",age,
                "Gender",genderName,
                "Tag",tagName)
                .addOnSuccessListener { documentReference ->
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .addOnFailureListener { e ->
                }

        }
    }
}