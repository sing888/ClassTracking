package com.project.group.rupp.dse.classtracking.activity
import PreferenceUtils
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.project.group.rupp.dse.classtracking.databinding.ActivitySignInBinding
import com.project.group.rupp.dse.classtracking.viewmodels.SigninViewModel
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.project.group.rupp.dse.classtracking.models.UiStateStatus


class SigninActivity: AppCompatActivity(){

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding!!

    private val signinViewModel: SigninViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInButton.setOnClickListener{
            var email = binding.textfillSignInEmail.editText?.text.toString()
            var password = binding.textfillSignInPassword.editText?.text.toString()
            if (email.isEmpty()){
                email = "Please enter your email"
                binding.textfillSignInEmail.error = "Please enter your email"
            }else{
                binding.textfillSignInEmail.error = null
            }
            if (password.isEmpty()){
                password = "Please enter your password"
                binding.textfillSignInPassword.error = "Please enter your password"
            }else{
                binding.textfillSignInPassword.error = null
            }

            if (!(email.isEmpty() && password.isEmpty())){

                signinViewModel.signIn(this ,email, password)

                signinViewModel.signinModelUiState.observe(this, Observer { uiState ->
                    when (uiState.status) {
                        UiStateStatus.loading -> {

                        }
                        UiStateStatus.success -> {
                            val token = uiState.data?.data?.token.toString()
                            PreferenceUtils.saveToken(this, token)
                            // start main activity
                            startActivity(
                                Intent(this, MainActivity::class.java))

                        }
                        UiStateStatus.error -> {
                            Toast.makeText(this, uiState.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

        }

        binding.signInToSignUp.setOnClickListener{
            // start sign up activity
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.forgotPassword.setOnClickListener{
            // start forgot password activity
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.signInGoogle.setOnClickListener{
            // start main activity
            startActivity(
                Intent(this, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}