package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderCheckAttendanceBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance

class TeacherCheckAttendanceViewHolder(private val binding: ViewHolderCheckAttendanceBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data: GetTeacherAttendance, position: Int){
                binding.tvNum.text = (position + 1).toString()
                binding.tvName.text = data.name

                when (data.attendance) {
                        true.toString() -> {
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_CHECKED
                        }
                        false.toString() -> {
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_INDETERMINATE
                        }
                        null.toString() -> {
                                binding.attendanceTeacherCheckbox.checkedState = MaterialCheckBox.STATE_UNCHECKED
                        }
                }

                binding.attendanceTeacherCheckbox.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                                Snackbar.make(
                                        binding.root,
                                        "Checked : ${data.name}",
                                        Snackbar.LENGTH_SHORT
                                ).show()
                        } else {
                                Snackbar.make(
                                        binding.root,
                                        "Unchecked : ${data.name}",
                                        Snackbar.LENGTH_SHORT
                                ).show()
                        }
                }
        }
}
