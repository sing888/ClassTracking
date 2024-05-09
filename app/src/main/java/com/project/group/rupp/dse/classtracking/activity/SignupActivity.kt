package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.project.group.rupp.dse.classtracking.databinding.ActivitySignUpBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.SignupViewModel

class SignupActivity: AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener{
            var username = binding.textfillSignUpUsername.editText?.text.toString()
            var email = binding.textfillSignUpEmail.editText?.text.toString()
            var password = binding.textfillSignUpPassword.editText?.text.toString()
            var confirmPassword = binding.textfillSignUpConfirmPassword.editText?.text.toString()

            checkInput(username, email, password, confirmPassword)

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword) {
                signupViewModel.signUp(this, username, email, password)

                signupViewModel.signupModelUiState.observe(this, Observer { uiState ->
                    when (uiState.status) {
                        UiStateStatus.loading -> {
                            binding.signUpButton.visibility = android.view.View.GONE
                            binding.signUpProgressBar.visibility = android.view.View.VISIBLE
                        }

                        UiStateStatus.success -> {
                            binding.signUpProgressBar.visibility = android.view.View.GONE
                            binding.signUpButton.visibility = android.view.View.VISIBLE
                            if (uiState.data == true) {
                                // start main activity
                                startActivity(Intent(this, SigninActivity::class.java))
                            }
                        }

                        UiStateStatus.error -> {
                            binding.signUpProgressBar.visibility = android.view.View.GONE
                            binding.signUpButton.visibility = android.view.View.VISIBLE
                            // show error message
                            Toast.makeText(this, "Sign up Uncompleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }

        binding.signUpToSignIn.setOnClickListener{
            // start sign in activity
            startActivity(Intent(this, SigninActivity::class.java))
        }

    }

    fun checkInput(username: String, email: String, password: String, confirmPassword: String){
        if (username.isEmpty()){
            binding.textfillSignUpUsername.error = "Please enter your username"
        }else{
            binding.textfillSignUpUsername.error = null
        }
        if (email.isEmpty()){
            binding.textfillSignUpEmail.error = "Please enter your email"
        }else{
            binding.textfillSignUpEmail.error = null
        }
        if (password.isEmpty()){
            binding.textfillSignUpPassword.error = "Please enter your password"
        }else{
            binding.textfillSignUpPassword.error = null
        }
        if (confirmPassword.isEmpty()){
            binding.textfillSignUpConfirmPassword.error = "Please confirm your password"
        }else{
            if (confirmPassword != password){
                binding.textfillSignUpConfirmPassword.error = "Password does not match"
            }else{
                binding.textfillSignUpConfirmPassword.error = null
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}