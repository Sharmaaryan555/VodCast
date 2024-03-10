package com.example.vodcast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vodcast.databinding.ActivitySignInAndRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import userData


class SignInAndRegistrationActivity : AppCompatActivity() {
    private val bindings:ActivitySignInAndRegistrationBinding by lazy {
        ActivitySignInAndRegistrationBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var storage:FirebaseStorage
    private val PICK_IMAGE_REQUEST =1
    private var imageUri : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)

//        Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://vodcast-8b94e-default-rtdb.asia-southeast1.firebasedatabase.app")
        storage = FirebaseStorage.getInstance()

        val action : String? = intent.getStringExtra("action")

        if (action == "login")
        {
           bindings.loginEmailAddress.visibility = View.VISIBLE
            bindings.loginPassword.visibility = View.VISIBLE
            bindings.LoginButton.visibility =View.VISIBLE

            bindings.registerButton.isEnabled = false
            bindings.registerButton.alpha = 0.5f
            bindings.registeruser.isClickable =false

            bindings.registerName.visibility = View.GONE
            bindings.registerEmail.visibility = View.GONE
            bindings.registerPassword.visibility = View.GONE
            bindings.cardView.visibility = View.GONE
        }
        else if (action == "register")
        {
            bindings.LoginButton.isEnabled = false
            bindings.LoginButton.alpha = 0.5f

            bindings.registerButton.setOnClickListener{
                val registerNAME: String = bindings.registerName.text.toString()
                val registerEMAIL: String = bindings.registerEmail.text.toString()
                val registerPASSWORD: String = bindings.registerPassword.text.toString()

                if (registerNAME.isEmpty() || registerEMAIL.isEmpty() || registerPASSWORD.isEmpty())
                {
                    Toast.makeText(this,"Please Fill All the Details",Toast.LENGTH_SHORT).show()
                }
                else{
                    auth.createUserWithEmailAndPassword(registerEMAIL,registerPASSWORD).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                                val user = auth.currentUser
                                 user?.let {
//                                Save User data in to Firebase realtime database
                                val userReference = database.getReference("users")
                                val userId = user.uid
                                val userData = userData(registerNAME,registerEMAIL)
                                userReference.child(userId).setValue(userData)
                                    .addOnSuccessListener{
                                    Log.d("TAG","onCreate: data saved")
                                }
                                    .addOnFailureListener{e ->
                                    Log.e("TAG","onCreate: Error on saving data ${e.message}")
                                    }
//                                upload image to firebase storage
                                val storageReference = storage.reference.child("profile_image/$userId.jpg")
//                                    storageReference.putFile(imageUri!!)

                                Toast.makeText(this,"User Register SuccessFully",Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this,"User Register Failed",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
        bindings.cardView.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"select Image"),
                PICK_IMAGE_REQUEST

                )
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.data !=null)
        {
            Glide.with(this).load(imageUri).apply(RequestOptions.circleCropTransform()).into(bindings.registerUserImage)
        }
    }
}