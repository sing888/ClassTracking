package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.project.group.rupp.dse.classtracking.fragment.AchieveFragment
import com.project.group.rupp.dse.classtracking.fragment.ProfileFragment
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.fragment.RoomFragment
import com.project.group.rupp.dse.classtracking.databinding.ActivityMainBinding
import com.project.group.rupp.dse.classtracking.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentContainer = binding.fragmentMainContainer
        val intent: Intent = intent
        val account_id = intent.getStringExtra("account_id")
        mainViewModel.setAccountId(account_id!!)

        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(fragmentContainer.id, RoomFragment())
        fragmentManager.commit()

        binding.mainBottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_room -> {
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    fragmentManager.replace(fragmentContainer.id, RoomFragment())
                    fragmentManager.commit()
                    true
                }

                R.id.menu_achieve -> {
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    fragmentManager.replace(fragmentContainer.id, AchieveFragment())
                    fragmentManager.commit()
                    true
                }

                R.id.menu_profile -> {
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    fragmentManager.replace(fragmentContainer.id, ProfileFragment())
                    fragmentManager.commit()
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}