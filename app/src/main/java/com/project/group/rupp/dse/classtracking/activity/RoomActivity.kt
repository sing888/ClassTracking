package com.project.group.rupp.dse.classtracking.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ActivityRoomBinding
import com.project.group.rupp.dse.classtracking.fragment.MoreRoomFragment
import com.project.group.rupp.dse.classtracking.fragment.TeacherNewsFragment
import com.project.group.rupp.dse.classtracking.fragment.StudentAttendanceFragment
import com.project.group.rupp.dse.classtracking.fragment.StudentNewsFragment
import com.project.group.rupp.dse.classtracking.fragment.StudentScoreFragment
import com.project.group.rupp.dse.classtracking.fragment.TeacherAttendanceFragment
import com.project.group.rupp.dse.classtracking.fragment.TeacherScoreFragment
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel

class RoomActivity: AppCompatActivity() {
    private var _binding: ActivityRoomBinding? = null
    private val binding get() = _binding!!
    private val roomViewModel: RoomMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // send data to fragment
        val intent: Intent = intent
        val roomtype = intent.getStringExtra("room_type")
        var roomid = intent.getStringExtra("room_id")
        val roomname = intent.getStringExtra("room_name")
        var roomcode = intent.getStringExtra("room_code")
        var roompassword = intent.getStringExtra("room_password")
        var account_id = intent.getStringExtra("account_id")

        roomViewModel.setRoomId(roomid!!)
        roomViewModel.setAccountId(account_id!!)
        roomViewModel.setRoomName(roomname!!)
        roomViewModel.setRoomType(roomtype!!)

        binding.roomBack.text = "<"
        
        val fragmentContainer = binding.llFragmentContainer
        val fragmentManager = supportFragmentManager.beginTransaction()
        when (roomtype){
            "teacher" -> {
                fragmentManager.add(fragmentContainer.id, TeacherNewsFragment())
                fragmentManager.commit()
            }
            "student" -> {
                fragmentManager.add(fragmentContainer.id, StudentNewsFragment())
                fragmentManager.commit()
            }
            else ->{}
        }
        true


        binding.roomName.text = roomname
        binding.roomBack.setOnClickListener() {
            finish()
        }

        binding.roomBottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.room_news -> {
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    when(roomtype){
                        "teacher" -> {
                            fragmentManager.replace(fragmentContainer.id, TeacherNewsFragment())
                            fragmentManager.commit()
                        }
                        "student" -> {
                            fragmentManager.replace(fragmentContainer.id, StudentNewsFragment())
                            fragmentManager.commit()
                        }
                        else -> {}
                    }
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
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    fragmentManager.replace(fragmentContainer.id, MoreRoomFragment())
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
