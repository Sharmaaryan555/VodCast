package com.example.vodcast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vodcast.databinding.ActivitySignInAndRegistrationBinding


class SignInAndRegistrationActivity : AppCompatActivity() {
   private val binding:ActivitySignInAndRegistrationBinding by lazy {
       ActivitySignInAndRegistrationBinding.inflate(layoutInflater)
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val action : String? = intent.getStringExtra("action")

        if (action == "login")
        {
           binding.loginEmailAddress.visibility = View.VISIBLE
            binding.loginPassword.visibility = View.VISIBLE
            binding.LoginButton.visibility =View.VISIBLE

            binding.registerButton.isClickable = false
            binding.registeruser.isClickable =false

            binding.registerName.visibility = View.GONE
            binding.registerEmail.visibility = View.GONE
            binding.registerPassword.visibility = View.GONE
            binding.cardView.visibility = View.GONE
        }
        else if (action == "register")
        {
            binding.loginEmailAddress.visibility = View.INVISIBLE
            binding.loginPassword.visibility = View.INVISIBLE
            binding.LoginButton.isClickable =false

            binding.registerButton.visibility = View.VISIBLE
            binding.registeruser.visibility =View.VISIBLE

            binding.registerName.visibility = View.VISIBLE
            binding.registerEmail.visibility = View.VISIBLE
            binding.registerPassword.visibility = View.VISIBLE
            binding.cardView.visibility = View.VISIBLE
        }
    }
}