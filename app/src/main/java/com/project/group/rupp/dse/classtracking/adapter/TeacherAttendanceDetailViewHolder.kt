package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherAttendanceDetailBinding
import com.project.group.rupp.dse.classtracking.models.GetAttendanceDetailAll

class TeacherAttendanceDetailViewHolder(private val binding: ViewHolderTeacherAttendanceDetailBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(data: GetAttendanceDetailAll, position: Int){
        binding.tvNum.text = (position + 1).toString()
        binding.tvName.text = data.name
        binding.tvScore.text = data.percentage.toString() + "% >"
        binding.tvPresent.text = "present: " + data.present.toString() + "/" + data.total.toString()
    }
}