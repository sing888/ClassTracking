package com.project.group.rupp.dse.classtracking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.adapter.TeacherCheckAttendanceAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentAttendanceTeacherBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherAttendanceViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TeacherAttendanceFragment: Fragment(){
    private var _binding: FragmentAttendanceTeacherBinding? = null
    private val binding get() = _binding!!
    private val teacherAttendanceViewModel: TeacherAttendanceViewModel by viewModels()
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()
    private val adapter = TeacherCheckAttendanceAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAttendanceTeacherBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputAttendanceDatetime.editText?.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        binding.inputAttendanceDatetime.setEndIconOnClickListener{
            showDatePickerDialog { selectedDate ->
                binding.inputAttendanceDatetime.editText?.setText(selectedDate)
                teacherAttendanceViewModel.getTeacherAttendance(requireContext(), roomMainViewModel.roomId.value!!, binding.inputAttendanceDatetime.editText?.text.toString())
            }

        }

        teacherAttendanceViewModel.teacherAttendanceUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    adapter.setDataset(uiState.data?.data!!)
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, uiState.message ?: "An error occurred", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        roomMainViewModel.roomId.observe(viewLifecycleOwner, Observer { classId ->
            teacherAttendanceViewModel.getTeacherAttendance(requireContext(), classId, binding.inputAttendanceDatetime.editText?.text.toString())
        })

        val LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.attendanceList.layoutManager = LinearLayoutManager
        binding.attendanceList.adapter = adapter

    }

    // Method to show a MaterialDatePicker dialog
    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Date")
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val datePicker = builder.build()
        datePicker.show(childFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            // Format the selected date as a string
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = Date(it)
            val selectedDate = dateFormat.format(date)
            // Call the onDateSelected function with the selected date
            onDateSelected(selectedDate)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
