package com.zeroone.haris.kotlinmessenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_btn.setOnClickListener{
            val email = email_et.text.toString()
            val password = password_et.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Invalid email/password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("MainActivity", email)
            Log.d("MainActivity",password)
            //Register new user with firebase
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (!it.isSuccessful){
                    Log.d("main", "onComplete: Failed=" + it.getException().toString())
                    return@addOnCompleteListener
                }
                //else if succesful
                Log.d("main: ", "createUserWithEmail:success")
            }
        }

        alreadyRegistered_tv.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }


}
