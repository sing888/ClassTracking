package com.project.group.rupp.dse.classtracking.activity

import android.content.Intent
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

        var intent: Intent = intent
        var roomtype = intent.getStringExtra("room_type")
        var roomid = intent.getStringExtra("room_id")
        var roomname = intent.getStringExtra("room_name")
        var roomcode = intent.getStringExtra("room_code")
        var roompassword = intent.getStringExtra("room_password")

        binding.roomName.text = roomname
        binding.roomTest.text = "Room Type: $roomtype\nRoom ID: $roomid\nRoom Name: $roomname\nRoom Code: $roomcode\nRoom Password: $roompassword"

        binding.roomBack.setOnClickListener(){
            finish()
        }

    }

}