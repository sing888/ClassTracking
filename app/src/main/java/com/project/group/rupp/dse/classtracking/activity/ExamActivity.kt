package com.project.group.rupp.dse.classtracking.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView.RecyclerListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.ExamAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityTeacherExamBinding
import com.project.group.rupp.dse.classtracking.models.GetExam
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

        var data = listOf<GetExam>()
        val adapter = ExamAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.roomName.text = className + "(${subjectName})"

        binding.roomBack.setOnClickListener {
            finish()
        }

        examViewModel.getExam(this, subjectId!!)
        adapter.setViewModel(examViewModel)

        examViewModel.examUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    binding.noExam.visibility = View.GONE
                    binding.recyclerViewRoom.visibility = View.VISIBLE
                    adapter.setData(it.data!!.data!!)
                    data = it.data.data!!
                    binding.recyclerViewRoom.adapter = adapter
                    binding.recyclerViewRoom.layoutManager = linearLayoutManager
                }
                UiStateStatus.error -> {
                    binding.noExam.visibility = View.VISIBLE
                    binding.recyclerViewRoom.visibility = View.GONE
                }
            }
        })

        adapter.setListener{ index: RecyclerView.ViewHolder ->
            val exam = data[index.adapterPosition]
            Snackbar.make(binding.root, "${data[index.adapterPosition].name}", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnAddExam.setOnClickListener {
            binding.flAddExam.visibility = RecyclerView.VISIBLE
        }

        binding.btnCancelExam.setOnClickListener {
            binding.ipQuizName.text?.clear()
            binding.ipScore.text?.clear()
            binding.flAddExam.visibility = RecyclerView.GONE
        }

        binding.btnSaveExam.setOnClickListener {
            val check = checkAddExam()

            if (check){
                examViewModel.addExam(
                    this,
                    binding.ipQuizName.text.toString(),
                    binding.ipScore.text.toString(),
                    subjectId
                )
                binding.ipQuizName.text?.clear()
                binding.ipScore.text?.clear()
                binding.flAddExam.visibility = RecyclerView.GONE
                binding.root.hideKeyboard()
            }


        }
        examViewModel.examAddUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Add exam success", Snackbar.LENGTH_SHORT).show()
                    examViewModel.getExam(this, subjectId)
                }
                UiStateStatus.error -> {
                    examViewModel.getExam(this, subjectId)
                }
            }
        })

        examViewModel.examDeleteUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Delete exam success", Snackbar.LENGTH_SHORT).show()
                    examViewModel.getExam(this, subjectId)
                }
                UiStateStatus.error -> {
                    examViewModel.getExam(this, subjectId)
                }
            }
        })
        
    }

    private fun checkAddExam(): Boolean{
        if (binding.ipScore.text.toString().isEmpty() || binding.ipQuizName.text.toString().isEmpty()){
            if (binding.ipScore.text.toString().isEmpty()){
                binding.ipScore.error = "Please enter max score"
            }else{
                binding.ipScore.error = null
            }
            if (binding.ipQuizName.text.toString().isEmpty()){
                binding.ipQuizName.error = "Please enter quiz name"
            }else{
                binding.ipQuizName.error = null
            }
            return false
        }else{
            try {
                // Try to parse the input as a float
                val score = binding.ipScore.text.toString().toFloat()

                // Check if the score is greater than 100
                if (score <= 0) {
                    // Set an error message if the score is greater than or equal to 100
                    binding.ipScore.error = "Max score must be greater than 0"
                    return false
                } else {
                    // Clear the error message if the score is valid and less than 100
                    binding.ipScore.error = null
                    return true
                }
            } catch (e: NumberFormatException) {
                // Set an error message if the input is not a valid float or integer
                binding.ipScore.error = "Invalid input. Please enter a number."
                return false
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}