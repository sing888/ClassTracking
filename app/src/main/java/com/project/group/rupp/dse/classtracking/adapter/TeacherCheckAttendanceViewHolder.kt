package com.project.group.rupp.dse.classtracking.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderCheckAttendanceBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherAttendanceViewModel

class TeacherCheckAttendanceViewHolder(private val binding: ViewHolderCheckAttendanceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetTeacherAttendance, position: Int, classroomId: String, date: String, viewmodel: TeacherAttendanceViewModel, context: Context){
                binding.tvNum.text = (position + 1).toString()
                binding.tvName.text = data.name

                when (data.attendance) {
                        true.toString() -> {
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_CHECKED
                                binding.attendanceTeacherCheckbox.isErrorShown = false
                                binding.attendanceTeacherCheckbox.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_primaryContainer)
                        }
                        false.toString() -> {
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_INDETERMINATE
                                binding.attendanceTeacherCheckbox.isErrorShown = true
                                binding.attendanceTeacherCheckbox.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_errorContainer)
                        }
                        null.toString() -> {
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_UNCHECKED
                        }
                }

                binding.attendanceTeacherCheckbox.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                                viewmodel.checkAttendance(context, data.member_id, true, classroomId, date)
                                binding.attendanceTeacherCheckbox.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_primaryContainer)
                                binding.attendanceTeacherCheckbox.isErrorShown = false
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_CHECKED
                        }
                        else {
                                viewmodel.checkAttendance(context, data.member_id, false, classroomId, date)
                                binding.attendanceTeacherCheckbox.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_errorContainer)
                                binding.attendanceTeacherCheckbox.isErrorShown = true
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_INDETERMINATE
                        }
                }
        }
}
