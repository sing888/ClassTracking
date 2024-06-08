package com.project.group.rupp.dse.classtracking.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.TeacherEditStudentAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityTeacherAttendanceEditStudentBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.MemberViewModel

class TeacherAttendanceEditStudent: AppCompatActivity(){
    private var _binding : ActivityTeacherAttendanceEditStudentBinding? = null
    private val binding get() = _binding!!

    private val memberViewModel: MemberViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTeacherAttendanceEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val classId = intent.getStringExtra("classroom_id")
        val className = intent.getStringExtra("room_name")

        val adapter = TeacherEditStudentAdapter()
        val LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.roomBack.text = "<"
        binding.roomName.text = "Add Student ($className)"

        binding.roomBack.setOnClickListener {
            finish()
        }

        memberViewModel.memberUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setDataset(it.data!!.data!!)
                    binding.rvStudentList.layoutManager = LinearLayoutManager
                    binding.rvStudentList.adapter = adapter
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })

        memberViewModel.getMember(this, classId!!)
        adapter.setViewModel(memberViewModel)

        binding.btnAddStudent.setOnClickListener {
            if (binding.studentName.text.toString().isEmpty()){
                binding.inputStudentName.error = "Please enter student name"
            }else {
                binding.inputStudentName.error = null
                memberViewModel.addMember(this, binding.studentName.text.toString(), classId)
            }
        }

        memberViewModel.memberAddUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Add student success", Snackbar.LENGTH_LONG).show()
                    binding.studentName.setText("")
                    memberViewModel.getMember(this, classId)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })

        memberViewModel.memberDeleteUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Delete success", Snackbar.LENGTH_LONG).show()
                    memberViewModel.getMember(this, classId)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })


    }
}