package com.project.group.rupp.dse.classtracking.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.ExamAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityTeacherExamBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.ExamViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherScoreViewModel

class ExamActivity: AppCompatActivity(){
    private var _binding: ActivityTeacherExamBinding? = null
    private val binding get() = _binding!!
    private val examViewModel: ExamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTeacherExamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val classId = intent.getStringExtra("classroom_id")
        val className = intent.getStringExtra("classroom_name")
        val subjectId = intent.getStringExtra("subject_id")
        val subjectName = intent.getStringExtra("subject_name")

        val adapter = ExamAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.roomName.text = className + "(${subjectName})"

        binding.roomBack.setOnClickListener {
            finish()
        }

        examViewModel.getExam(this, subjectId!!)

        examViewModel.examUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setData(it.data!!.data!!)
                    binding.recyclerViewRoom.adapter = adapter
                    binding.recyclerViewRoom.layoutManager = linearLayoutManager
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
        
    }
}