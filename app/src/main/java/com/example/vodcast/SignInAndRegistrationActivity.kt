package com.example.vodcast

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vodcast.databinding.ActivitySignInAndRegistrationBinding


class SignInAndRegistrationActivity : AppCompatActivity() {
    private val bindings:ActivitySignInAndRegistrationBinding by lazy {
        ActivitySignInAndRegistrationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindings.root)

        val action : String? = intent.getStringExtra("action")

        if (action == "login")
        {
           bindings.loginEmailAddress.visibility = View.VISIBLE
            bindings.loginPassword.visibility = View.VISIBLE
            bindings.LoginButton.visibility =View.VISIBLE

            bindings.registerButton.isClickable = false
            bindings.registeruser.isClickable =false

            bindings.registerName.visibility = View.GONE
            bindings.registerEmail.visibility = View.GONE
            bindings.registerPassword.visibility = View.GONE
            bindings.cardView.visibility = View.GONE
        }
        else if (action == "register")
        {
            bindings.loginEmailAddress.visibility = View.INVISIBLE
            bindings.loginPassword.visibility = View.INVISIBLE
            bindings.LoginButton.isEnabled =false

            bindings.registerButton.visibility = View.VISIBLE
            bindings.registeruser.visibility =View.VISIBLE

            bindings.registerName.visibility = View.VISIBLE
            bindings.registerEmail.visibility = View.VISIBLE
            bindings.registerPassword.visibility = View.VISIBLE
            bindings.cardView.visibility = View.VISIBLE
        }
    }
}