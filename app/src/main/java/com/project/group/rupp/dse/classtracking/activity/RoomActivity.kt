package com.project.group.rupp.dse.classtracking.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.group.rupp.dse.classtracking.databinding.ActivityRoomBinding

class RoomActivity: AppCompatActivity(){
    private var _binding: ActivityRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}