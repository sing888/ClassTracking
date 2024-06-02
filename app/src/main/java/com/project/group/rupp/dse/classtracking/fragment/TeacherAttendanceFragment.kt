package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
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
import com.project.group.rupp.dse.classtracking.activity.TeacherAttendanceDetailActivity
import com.project.group.rupp.dse.classtracking.activity.TeacherAttendanceEditStudent
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
    val LinearLayoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
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
        adapter.setDate(binding.inputAttendanceDatetime.editText?.text.toString())
        adapter.setClassroomId(roomMainViewModel.roomId.value!!)
        adapter.setViewModel(teacherAttendanceViewModel)
        adapter.setContext(requireContext())
        teacherAttendanceViewModel.getTeacherAttendance(requireContext(), roomMainViewModel.roomId.value!!, binding.inputAttendanceDatetime.editText?.text.toString())

        binding.inputAttendanceDatetime.setEndIconOnClickListener{
            showDatePickerDialog { selectedDate ->
                binding.inputAttendanceDatetime.editText?.setText(selectedDate)
                adapter.setDate(selectedDate)
                teacherAttendanceViewModel.getTeacherAttendance(requireContext(), roomMainViewModel.roomId.value!!, selectedDate)
            }

        }

        teacherAttendanceViewModel.teacherAttendanceUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    binding.noAttendance.visibility = View.GONE
                    adapter.setDataset(uiState.data?.data ?: listOf())
                    binding.attendanceList.layoutManager = LinearLayoutManager
                    binding.attendanceList.adapter = adapter
                }
                UiStateStatus.error -> {
                    binding.noAttendance.visibility = View.VISIBLE
                }
            }
        })

        teacherAttendanceViewModel.teacherCheckAttendanceUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Success", Snackbar.LENGTH_SHORT).show()
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, uiState.message ?: "An error occurred", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        roomMainViewModel.roomId.observe(viewLifecycleOwner, Observer { classId ->
            teacherAttendanceViewModel.getTeacherAttendance(requireContext(), classId, binding.inputAttendanceDatetime.editText?.text.toString())
        })

        binding.attendanceTeacherSeeDetail.setOnClickListener {
            if (adapter.itemCount == 0) {
                Snackbar.make(binding.root, "No attendance data", Snackbar.LENGTH_SHORT).show()
            }else {
                val intent = Intent(requireContext(), TeacherAttendanceDetailActivity::class.java)
                intent.putExtra("classroom_id", roomMainViewModel.roomId.value)
                intent.putExtra("room_name", roomMainViewModel.roomName.value)
                startActivity(intent)
            }
        }

        binding.attendanceTeacherEdit.setOnClickListener {
            val intent = Intent(requireContext(), TeacherAttendanceEditStudent::class.java)
            intent.putExtra("classroom_id", roomMainViewModel.roomId.value)
            intent.putExtra("room_name", roomMainViewModel.roomName.value)
            startActivity(intent)
        }


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
