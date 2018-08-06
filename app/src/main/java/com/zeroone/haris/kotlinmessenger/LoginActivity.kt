package com.zeroone.haris.kotlinmessenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener{
            val email = email_et_login.text.toString()
            val password = password_et_login.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please enter Email and Password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(!it.isSuccessful){
                    Log.d("Main","Login failed!")
                    Toast.makeText(this,"Invalid Email or Password",Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }
                //login successful
                Log.d("Main","Login :Successful")
                val intent = Intent(this,LatestMessagesActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        newRegister_tv.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
