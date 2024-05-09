package com.project.group.rupp.dse.classtracking.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ActivityRoomBinding
import com.project.group.rupp.dse.classtracking.fragment.NewsFragment
import com.project.group.rupp.dse.classtracking.fragment.StudentAttendanceFragment
import com.project.group.rupp.dse.classtracking.fragment.StudentScoreFragment
import com.project.group.rupp.dse.classtracking.fragment.TeacherAttendanceFragment
import com.project.group.rupp.dse.classtracking.fragment.TeacherScoreFragment

class RoomActivity: AppCompatActivity() {
    private var _binding: ActivityRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentContainer = binding.llFragmentContainer
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.add(fragmentContainer.id, NewsFragment())
        fragmentManager.commit()

        val intent: Intent = intent
        val roomtype = intent.getStringExtra("room_type")
        var roomid = intent.getStringExtra("room_id")
        val roomname = intent.getStringExtra("room_name")
        var roomcode = intent.getStringExtra("room_code")
        var roompassword = intent.getStringExtra("room_password")

        binding.roomName.text = roomname
//        binding.roomTest.text =
//            "Room Type: $roomtype\nRoom ID: $roomid\nRoom Name: $roomname\nRoom Code: $roomcode\nRoom Password: $roompassword"

        binding.roomBack.setOnClickListener() {
            finish()
        }

        binding.roomBottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.room_news -> {
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    fragmentManager.replace(fragmentContainer.id, NewsFragment())
                    fragmentManager.commit()
                    true
                }

                R.id.room_attendance -> {
                    when (roomtype) {
                        "teacher" -> {
                            val fragmentManager = supportFragmentManager.beginTransaction()
                            fragmentManager.replace(fragmentContainer.id, TeacherAttendanceFragment())
                            fragmentManager.commit()

                        }
                        "student" -> {
                            val fragmentManager = supportFragmentManager.beginTransaction()
                            fragmentManager.replace(fragmentContainer.id, StudentAttendanceFragment())
                            fragmentManager.commit()
                        }
                        else -> {}

                    }
                    true
                }

                R.id.room_score -> {
                    when (roomtype) {
                        "teacher" -> {
                            val fragmentManager = supportFragmentManager.beginTransaction()
                            fragmentManager.replace(fragmentContainer.id, TeacherScoreFragment())
                            fragmentManager.commit()

                        }
                        "student" -> {
                            val fragmentManager = supportFragmentManager.beginTransaction()
                            fragmentManager.replace(fragmentContainer.id, StudentScoreFragment())
                            fragmentManager.commit()
                        }
                        else -> {}

                    }
                    true
                }

                R.id.room_more -> {
                    // Respond to navigation item 4 click
                    true
                }

                else -> false
            }

        }
    }
}