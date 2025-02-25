package com.project.group.rupp.dse.classtracking.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.TeacherAttendanceDetailAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityAttendanceDetailBinding
import com.project.group.rupp.dse.classtracking.databinding.ActivityTeacherAttendanceDetailBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherAttendanceDetailViewModel

class TeacherAttendanceDetailActivity: AppCompatActivity(){
    private var _binding: ActivityTeacherAttendanceDetailBinding ?= null
    private val binding get() = _binding!!

    private val teacherAttendanceDetailViewModel: TeacherAttendanceDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTeacherAttendanceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val classId = intent.getStringExtra("classroom_id")
        val roomName = intent.getStringExtra("room_name")

        val adapter = TeacherAttendanceDetailAdapter()
        val LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.roomName.text = "Attendance Detail (" + roomName + ")"

        binding.roomBack.text = "<"
        binding.roomBack.setOnClickListener {
            finish()
        }

        binding.swipeRefreshContainer.setOnRefreshListener {
            onRefresh(classId.toString())
        }


        teacherAttendanceDetailViewModel.attendanceDetailUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setDataset(uiState.data!!.data!!)
                    binding.attendanceList.layoutManager = LinearLayoutManager
                    binding.attendanceList.adapter = adapter
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        teacherAttendanceDetailViewModel.getAttendanceDetail(this, classId.toString())




    }

    private fun onRefresh(classId: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            teacherAttendanceDetailViewModel.getAttendanceDetail(this, classId)
            Toast.makeText(this, "Refreshed", Toast.LENGTH_LONG).show()
            binding.swipeRefreshContainer.isRefreshing = false
        }, 300)
    }
}