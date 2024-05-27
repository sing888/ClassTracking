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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.RoomActivity
import com.project.group.rupp.dse.classtracking.adapter.StudentAttendanceAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentAttendanceStudentBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.StudentAttendanceViewModel

class StudentAttendanceFragment: Fragment(){
    private var _binding: FragmentAttendanceStudentBinding? = null
    private val binding get() = _binding!!
    private val studentAttendanceViewModel: StudentAttendanceViewModel by viewModels()
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAttendanceStudentBinding.inflate(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adapter = StudentAttendanceAdapter()

        studentAttendanceViewModel.attendancemodelUiState.observe(viewLifecycleOwner, Observer{ uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.attendanceStudentNoData.visibility = View.GONE
                    binding.attendanceStudentCardRecyclerView.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.attendanceStudentNoData.visibility = View.GONE
                    binding.attendanceStudentCardRecyclerView.visibility = View.VISIBLE
                    binding.attendanceStudentPercentage.text = uiState.data?.data?.percentage.toString() + "%"
                    binding.attendanceStudentPercentageMax.text = "Max: 100%"
                    binding.attendanceStudentAbsent.text = (uiState.data?.data?.total!! - uiState.data.data.active).toString()
                    binding.attendanceStudentAbsentMax.text = "Max: " + uiState.data?.data?.total.toString()
                }
                UiStateStatus.error -> {
                    binding.attendanceStudentNoData.visibility = View.VISIBLE
                    binding.attendanceStudentCardRecyclerView.visibility = View.GONE
                }
            }
        })

        studentAttendanceViewModel.attendanceDetailUiState.observe(viewLifecycleOwner, Observer{ uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.attendanceStudentNoData.visibility = View.GONE
                    binding.attendanceStudentCardRecyclerView.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.attendanceStudentNoData.visibility = View.GONE
                    binding.attendanceStudentCardRecyclerView.visibility = View.VISIBLE
                    adapter.setDataset(uiState.data?.data!!)
                }
                UiStateStatus.error -> {
                    binding.attendanceStudentNoData.visibility = View.VISIBLE
                    binding.attendanceStudentCardRecyclerView.visibility = View.GONE
                }
            }
        })

        roomMainViewModel.roomId.observe(viewLifecycleOwner, Observer { roomId ->
            studentAttendanceViewModel.getStudentAttendance(requireContext(), roomId)
            studentAttendanceViewModel.getStudentAttendanceDetail(requireContext(), roomId)
        })

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.attendanceStudentRecyclerView.layoutManager = layoutManager
        binding.attendanceStudentRecyclerView.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}