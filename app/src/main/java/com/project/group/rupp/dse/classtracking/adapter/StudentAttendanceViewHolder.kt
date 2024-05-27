package com.project.group.rupp.dse.classtracking.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAttendanceBinding
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendanceDetail

class StudentAttendanceViewHolder(private val binding: ViewHolderAttendanceBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: GetStudentAttendanceDetail){
            binding.tvTime.text = data.date
            if (data.active) {
                binding.attendanceStudentCheckbox.checkedState = MaterialCheckBox.STATE_CHECKED
                binding.attendanceStudentCheckbox.isErrorShown = false
                binding.attendanceStudentCheckbox.isClickable = false
            }else{
                binding.attendanceStudentCheckbox.checkedState = MaterialCheckBox.STATE_INDETERMINATE
                binding.attendanceStudentCheckbox.isErrorShown = true
                binding.attendanceStudentCardView.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_errorContainer)
                binding.attendanceStudentCheckbox.isClickable = false
            }
        }
}