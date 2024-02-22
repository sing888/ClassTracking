package com.project.group.rupp.dse.classtracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.group.rupp.dse.classtracking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_main_container, RoomFragment()).commit()

        binding.mainBottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_room -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_main_container, RoomFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_achieve -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_main_container, AchieveFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_main_container, ProfileFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }

    }
}