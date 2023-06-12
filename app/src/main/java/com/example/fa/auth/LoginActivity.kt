package com.example.fa.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fa.R
import com.example.fa.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvCreateUser: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide();
        etEmail=findViewById(R.id.etLogin)
        etPass=findViewById(R.id.etPass)
        btnLogin=findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener{Login() }
        tvCreateUser=findViewById(R.id.tvCreateNewUser)
        tvCreateUser.setOnClickListener { startActivity(Intent(this,RegActivity::class.java)) }

    }
    fun Login(){
        var pass=etPass.text.toString()
        var login=etEmail.text.toString()

        auth= FirebaseAuth.getInstance()
        if(TextUtils.isEmpty(login) && TextUtils.isEmpty(pass) ){
            //Toast.makeText(this, "Пустые поля", Toast.LENGTH_SHORT).show()
        }
        if(TextUtils.isEmpty(login)) { Toast.makeText(this, "Введите email", Toast.LENGTH_SHORT).show()
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
        }
        else{
            auth.signInWithEmailAndPassword(login,pass).addOnCompleteListener(this){
                    task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    // Toast.makeText(this,"Авторизация прошла успешно", Toast.LENGTH_SHORT).show()
                }
                else{
                    // Toast.makeText(this, "Авторизация не удалась", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}