package com.zeroone.haris.kotlinmessenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URI
import java.util.*

class MainActivity : AppCompatActivity() {
    var selectedpictureUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //listener for add photo button
        addPicture_btn.setOnClickListener{
            //an action pick intent allow us to pick a resource
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*" //choose form the image directory
            startActivityForResult(intent,0)
        }

        //listener for register button
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
                uploadImagetoFirebase()
            }
        }

        alreadyRegistered_tv.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode== Activity.RESULT_OK && data != null){
            //add photo
            //get uri of image
            selectedpictureUri = data.data
            //get bitmap of image
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedpictureUri)
            circleimageview.setImageBitmap(bitmap)
            addPicture_btn.alpha=0f
            //val bitmapDrawable = BitmapDrawable(bitmap)
            //addPicture_btn.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun uploadImagetoFirebase(){
        if (selectedpictureUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedpictureUri!!).
                addOnSuccessListener {
                    Log.d("Main","Sucessfully uploaded picture")
                    ref.downloadUrl.addOnSuccessListener {
                        Log.d("Main","File location:$it")
                        saveUsertoDatabase(it.toString())
                    }
                }
        //!! is there to unwrap the uri from type Uri? to Uri

    }

    private fun saveUsertoDatabase(pictureUrl: String) {
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user=User(uid,username_et.text.toString(),pictureUrl)

        ref.setValue(user).addOnSuccessListener {
            Log.d("Main","Saved user successfully!")
        }.addOnFailureListener {
            Log.d("Main","$uid $it.message.toString()")
        }
    }


}

class User(val uid: String,val username:String, val pictureUrl: String)