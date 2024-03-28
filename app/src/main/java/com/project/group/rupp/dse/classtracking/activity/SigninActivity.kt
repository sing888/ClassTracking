package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.project.group.rupp.dse.classtracking.databinding.ActivitySignInBinding

class SigninActivity: Activity(){

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener{
            // start main activity
            startActivity(
                Intent(this, MainActivity::class.java))
        }

        binding.signInToSignUp.setOnClickListener{
            // start sign up activity
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.forgotPassword.setOnClickListener{
            // start forgot password activity
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}