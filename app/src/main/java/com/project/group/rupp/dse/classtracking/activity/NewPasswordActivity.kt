package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.project.group.rupp.dse.classtracking.databinding.ActivityNewPasswordBinding

class NewPasswordActivity: Activity(){

    private var _binding: ActivityNewPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newPasswordButton.setOnClickListener{
            // start sign in activity
            startActivity(Intent(this, SigninActivity::class.java))
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