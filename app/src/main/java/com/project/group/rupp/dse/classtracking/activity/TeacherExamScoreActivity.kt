package com.project.group.rupp.dse.classtracking.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.adapter.ExamScoreAdapter
import com.project.group.rupp.dse.classtracking.databinding.ActivityExamScoreTeacherBinding
import com.project.group.rupp.dse.classtracking.models.GetExamScoreDetail
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.ExamScoreViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.ExamViewModel

class TeacherExamScoreActivity: AppCompatActivity() {
    private var _binding : ActivityExamScoreTeacherBinding? = null
    private val binding get() = _binding!!

    private val examScoreViewModel: ExamScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityExamScoreTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var clickscore: GetExamScoreDetail ? = null
        var data : List<GetExamScoreDetail> = listOf()
        val adapter = ExamScoreAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.roomBack.text = "<"
        binding.roomBack.setOnClickListener {
            finish()
        }

        val intent = intent
        val examId = intent.getStringExtra("exam_id")
        val examName = intent.getStringExtra("exam_name")

        binding.roomName.text = examName

        examScoreViewModel.getExamScoreDetail(this, examId!!)

        examScoreViewModel.examScoreDetailUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setData(it.data!!.data!!)
                    data = it.data.data!!
                    binding.rvExamScore.adapter = adapter
                    binding.rvExamScore.layoutManager = linearLayoutManager
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        adapter.setListener{ index: RecyclerView.ViewHolder ->
            clickscore = data[index.adapterPosition]
            binding.llAddScore.visibility = if (binding.llAddScore.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            binding.tvAddScoreName.text = clickscore!!.name
            binding.tvAddScoreMaxScore.text = clickscore!!.max_score.toString()
        }

        binding.btnCancelExam.setOnClickListener {
            binding.ipScore.setText("")
            binding.llAddScore.visibility = View.GONE
        }

        binding.btnSaveExam.setOnClickListener {
            val score = binding.ipScore.text.toString()
            if (score.isEmpty()){
                binding.ipScore.error = "Please input score"
            }else{
                val ipScore =score.toFloatOrNull()
                if (ipScore == null) {
                    binding.ipScore.error = "Please input number"
                }else if(ipScore > binding.tvAddScoreMaxScore.text.toString().toFloat()){
                    binding.ipScore.error = "Score must be less than or equal to ${binding.tvAddScoreMaxScore.text}"
                }else{
                    binding.ipScore.error = null
                    examScoreViewModel.addExamScore(this, score, examId, clickscore!!.member_id)
                    binding.ipScore.setText("")
                    binding.llAddScore.visibility = View.GONE
                    binding.root.hideKeyboard()
                }
            }
        }

        examScoreViewModel.addExamScoreUiState.observe(this, Observer {
            when(it.status){
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Add score success", Snackbar.LENGTH_SHORT).show()
                    examScoreViewModel.getExamScoreDetail(this, examId)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, "Add score failed", Snackbar.LENGTH_SHORT).show()

                }
            }
        })


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