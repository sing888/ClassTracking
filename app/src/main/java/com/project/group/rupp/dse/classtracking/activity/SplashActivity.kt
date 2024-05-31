package com.project.group.rupp.dse.classtracking.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.project.group.rupp.dse.classtracking.databinding.ActivitySplashBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.ProfileViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.SplashViewModel

class SplashActivity: AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private val splashViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = PreferenceUtils.getToken(this)

        if (token != null){
            splashViewModel.getProfile(this)
            splashViewModel.profileModelUiState.observe(this, Observer { uiState ->
                when (uiState.status) {
                    UiStateStatus.loading -> {

                    }
                    UiStateStatus.success -> {
                        val account_id = uiState.data?.data?.account_id
                        if (account_id != null) {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("account_id", account_id)
                            startActivity(intent)
                        }
                    }
                    UiStateStatus.error -> {
                        startActivity(Intent(this, SigninActivity::class.java))
                    }
                }
            })
    }
        else{
            startActivity(Intent(this, SigninActivity::class.java))
        }
    }
}