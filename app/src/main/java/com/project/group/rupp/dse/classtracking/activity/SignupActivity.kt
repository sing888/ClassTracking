package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.project.group.rupp.dse.classtracking.databinding.ActivitySignUpBinding

class SignupActivity: Activity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener{
            // start main activity
            startActivity(Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        }

        binding.signUpToSignIn.setOnClickListener{
            // start sign in activity
            startActivity(Intent(this, SigninActivity::class.java))
        }
    }


}