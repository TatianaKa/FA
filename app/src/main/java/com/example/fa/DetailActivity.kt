package com.example.fa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val bundle:Bundle?=intent.extras
        val name= bundle?.getString("name")
        val tvDetail=findViewById<TextView>(R.id.tvDetail)
        tvDetail.setText(name)
    }
}