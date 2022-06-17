package com.example.recipegenie.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.recipegenie.MainActivity
import com.example.recipegenie.R
import com.example.recipegenie.view.registration.LoginActivity
import com.example.recipegenie.view.registration.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth

class LandingPage : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var sign_in_btn :Button
    lateinit var sign_up : TextView
    lateinit var guest :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        if (currentuser != null){
            startActivity(android.content.Intent(this, MainActivity::class.java))
            finish()
        }

        var bntSignIn : Button = findViewById(R.id.btn_sign_in)
        bntSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        sign_up = findViewById(R.id.sign_up)
        sign_up.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@LandingPage, RegistrationActivity::class.java))
            }
        })
    }
}