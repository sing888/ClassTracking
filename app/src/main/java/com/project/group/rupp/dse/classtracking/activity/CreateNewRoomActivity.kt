package com.project.group.rupp.dse.classtracking.activity

import PreferenceUtils
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.MenuRes
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ActivityCreateNewRoomBinding
import com.project.group.rupp.dse.classtracking.fragment.CreateRoomFragment
import com.project.group.rupp.dse.classtracking.fragment.JoinRoomFragment

class CreateNewRoomActivity: AppCompatActivity(){
    private var __binding: ActivityCreateNewRoomBinding? = null
    private val binding get() = __binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        __binding = ActivityCreateNewRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start screen
        val fragmentContainer = binding.fragmentCreateNewRoom
        startScreen(binding, fragmentContainer)

        binding.roomBack.text = "<"

        binding.roomBack.setOnClickListener {
            finish()
        }

        binding.btnCreateRoom.setOnClickListener {
            binding.roomName.text = "Create New Room"
            binding.btnCreateRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_primaryContainer);
            binding.btnJoinRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface);
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.replace(fragmentContainer.id, CreateRoomFragment())
            fragmentManager.commit()

        }
        binding.btnJoinRoom.setOnClickListener {
            binding.roomName.text = "Join New Room"
            binding.btnJoinRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_primaryContainer);
            binding.btnCreateRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface);
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.replace(fragmentContainer.id, JoinRoomFragment())
            fragmentManager.commit()


        }

    }

    private fun startScreen(binding: ActivityCreateNewRoomBinding, fragmentContainer: View){
        var intent: Intent = intent
        var head = intent.getStringExtra("header")

        binding.roomName.text = head

        if (head == "Create New Room") {
            binding.btnCreateRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_primaryContainer);
            binding.btnJoinRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface);
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.replace(fragmentContainer.id, CreateRoomFragment())
            fragmentManager.commit()
        } else {
            binding.btnJoinRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_primaryContainer);
            binding.btnCreateRoom.backgroundTintList = ContextCompat.getColorStateList(this, R.color.md_theme_surface);
            val fragmentManager = supportFragmentManager.beginTransaction()
            fragmentManager.replace(fragmentContainer.id, JoinRoomFragment())
            fragmentManager.commit()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        __binding = null
    }
}