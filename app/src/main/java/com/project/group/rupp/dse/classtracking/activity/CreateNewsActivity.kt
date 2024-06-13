package com.project.group.rupp.dse.classtracking.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.group.rupp.dse.classtracking.databinding.ActivityCreateNewsBinding
import com.project.group.rupp.dse.classtracking.viewmodels.NewsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale.getDefault

class CreateNewsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding: ActivityCreateNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val header = intent.getStringExtra("header")
        val classroomId = intent.getStringExtra("classroom_id")

        binding.roomName.text = header

        binding.newsBack.text = "<"

        binding.newsBack.setOnClickListener {
            finish()
        }
        binding.swipeRefreshContainer.setOnRefreshListener(this)

        binding.postButton.setOnClickListener {
            val newsTitle = binding.inputNewsTitle.editText?.text.toString()
            val newsDescription = binding.inputNewsDescription.editText?.text.toString()
            val newsDeadline = binding.inputNewsDatetime.editText?.text.toString()
            if (newsTitle.isEmpty()){
                binding.inputNewsTitle.error = "Please enter the title"
            }else{
                binding.inputNewsTitle.error = null
            }
            if (newsDeadline.isEmpty()){
                binding.inputNewsDatetime.error = "Please enter the deadline"
            }else{
                binding.inputNewsDatetime.error = null
            }
            if (newsDescription.isEmpty()){
                binding.inputNewsDescription.error = "Please enter the description"
            }else{
                binding.inputNewsDescription.error = null
            }
            if (newsTitle.isNotEmpty() && newsDescription.isNotEmpty() && newsDeadline.isNotEmpty()) {
                Log.d("ButtonClicked", "Post button clicked")
                if (classroomId != null) {
                    Log.d("CreateNewsActivity", "Class ID not missing")
                    newsViewModel.postNews(this, newsTitle, newsDeadline, newsDescription, classroomId)
                    finish()
                } else {
                    Log.e("CreateNewsActivity1", "Classroom ID is missing")
                }
            } else {
                Log.e("CreateNewsActivity", "Classroom ID is missing")
            }
        }

        binding.inputNewsDatetime.setEndIconOnClickListener {
            showDatePickerDialog { selectedDate ->
                binding.inputNewsDatetime.editText?.setText(selectedDate)
            }
        }
    }

    private fun showDatePickerDialog(onDataSelected: (String) -> Unit) {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Date")
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val datePicker = builder.build()
        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", getDefault())
            val selectedDate = dateFormat.format(Date(it))
            onDataSelected(selectedDate)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity(intent)
            Toast.makeText(this, "Refreshed", Toast.LENGTH_LONG).show()
            binding.swipeRefreshContainer.isRefreshing = false
        }, 300)

    }
}
