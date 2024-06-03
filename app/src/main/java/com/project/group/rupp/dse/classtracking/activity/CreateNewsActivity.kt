package com.project.group.rupp.dse.classtracking.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.group.rupp.dse.classtracking.databinding.ActivityCreateNewsBinding
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherNewsViewModel
import java.text.SimpleDateFormat
import androidx.fragment.app.FragmentManager
import java.util.Date
import java.util.Locale

class CreateNewsActivity: AppCompatActivity() {
    private var _binding: ActivityCreateNewsBinding? = null

    private val binding get() = _binding!!

    private val teacherNewsViewModel: TeacherNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCreateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startScreen(this, CreateNewsActivity::class.java)

        binding.newsBack.setOnClickListener({
            finish()
        })

    }
    private fun startScreen(context: Context, activityClass: Class<*>){
        var intent: Intent = intent
        var head = intent.getStringExtra("header")

        binding.roomName.text = head

        binding.postButton.setOnClickListener {
            val newsTitle = binding.inputNewsTitle.toString()
            val newsDescription = binding.inputNewsDescription.toString()
            val newsDeadline = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            binding.inputNewsDatetime.editText?.setText(newsDeadline)

            binding.inputNewsDatetime.setEndIconOnClickListener {
                showDatePickerDialog { selectedDate ->
                    val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                    binding.inputNewsDatetime.editText?.setText(formattedDate)
                    teacherNewsViewModel.getTeacherNews(this, formattedDate)
                }
            }

            if (newsTitle.isEmpty()){
                binding.inputNewsTitle.error = "Please enter the news title."
            }else{
                binding.inputNewsTitle.error = null
            }

            if (newsDeadline.isEmpty()){
                binding.inputNewsDescription.error = "Please enter the news deadline."
            }else{
                binding.inputNewsDescription.error = null
            }

            if (newsDescription.isEmpty()){
                binding.inputNewsDescription.error = "Please enter the news description."
            }else{
                binding.inputNewsDescription.error = null
            }

            if (newsTitle.isNotEmpty() && newsDeadline.isNotEmpty() && newsDescription.isNotEmpty()){
                teacherNewsViewModel.postNews(this, newsTitle, newsDeadline, newsDescription)
            }

        }

    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Date")
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val datePicker = builder.build()
        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            // Format the selected date as a string
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date(it)
            val selectedDate = dateFormat.format(date)
            // Call the onDateSelected function with the selected date
            onDateSelected(selectedDate)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}