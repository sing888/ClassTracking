package com.project.group.rupp.dse.classtracking.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.StudentScoreDetailScoreAdapter
import com.project.group.rupp.dse.classtracking.adapter.StudentScoreDetailSubjectScoreAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityScoreStudentDetailBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.StudentScoreDetailViewModel

class StudentScoreDetail: AppCompatActivity(){
    private var _binding: ActivityScoreStudentDetailBinding ?= null
    private val binding get() = _binding!!
    private val studentScoreDetailViewModel: StudentScoreDetailViewModel by viewModels()
    private val subjectAdapter = StudentScoreDetailSubjectScoreAdapter()
    private val scoreAdapter = StudentScoreDetailScoreAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityScoreStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val member_Name = intent.getStringExtra("member_name")
        val member_Id = intent.getStringExtra("member_id")
        val class_Id = intent.getStringExtra("classroom_id")

        binding.tvName.text = member_Name.toString()
        binding.ivBack.setOnClickListener {
            finish()
        }

        studentScoreDetailViewModel.studentScoreDetailUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    binding.averageScoreValue.text = uiState.data?.data?.average_score.toString()
                    subjectAdapter.setSubjectScore(uiState.data?.data?.subject_score ?: listOf())
                    scoreAdapter.setScore(uiState.data?.data?.score ?: listOf())
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        studentScoreDetailViewModel.getStudentScoreDetail(this, memberId = member_Id.toString(), classId = class_Id.toString())

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcAverage.layoutManager = layoutManager
        binding.rcAverage.adapter = subjectAdapter
        binding.rcDetails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rcDetails.adapter = scoreAdapter
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}