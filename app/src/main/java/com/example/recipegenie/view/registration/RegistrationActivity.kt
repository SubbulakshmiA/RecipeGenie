package com.example.recipegenie.view.registration

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.recipegenie.R
import com.example.recipegenie.view.LandingPage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? =null
    var database : FirebaseDatabase? = null
    lateinit var sign_up : Button
    lateinit var cancel_btn : Button
    lateinit var username : TextInputEditText
    lateinit var password : TextInputEditText
    lateinit var confirm_password : TextInputEditText
    lateinit var email_id : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        sign_up = findViewById(R.id.sign_up)
        cancel_btn = findViewById(R.id.cancel_btn)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        confirm_password = findViewById(R.id.confirm_password)
        email_id = findViewById(R.id.email_id)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()

    }
    private fun register(){
        sign_up.setOnClickListener {

            if(TextUtils.isEmpty( username.text.toString())){
                username.setError("Plaese Enter User Name")
                return@setOnClickListener
            }else if (TextUtils.isEmpty( password.text.toString())){
                password.setError("Plaese Enter Password")
                return@setOnClickListener
            }else if (TextUtils.isEmpty( confirm_password.text.toString())){
                confirm_password.setError("Plaese Enter Confirm Password")
                return@setOnClickListener
            }else if (TextUtils.isEmpty( email_id.text.toString())){
                email_id.setError("Plaese Enter Email-Id")
                return@setOnClickListener
            }
            println("sign up before auth")

            auth.createUserWithEmailAndPassword(email_id.text.toString(),password.text.toString())
                .addOnCompleteListener(this, OnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Log.d("AppDatabase","AAA to 1")
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Log.d("AppDatabase","AAA else 1")
                        val builder = AlertDialog.Builder(this@RegistrationActivity)
                        builder.setMessage("User Already Exist. Login with Email-Id and Password or Register with another Email-Id")
                        builder.setCancelable(true)
                        builder.setNegativeButton("OK", DialogInterface.OnClickListener
                        { dialog, which -> dialog.cancel() })
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.show()
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
        }
        cancel_btn.setOnClickListener {
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)

        }

    }




}