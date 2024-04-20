package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.project.group.rupp.dse.classtracking.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity: Activity(){

    private var _binding: ActivityForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPasswordButton.setOnClickListener{
            // start sign in activity
            startActivity(Intent(this, NewPasswordActivity::class.java))
        }

        binding.backButton.setOnClickListener{
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}