package com.project.group.rupp.dse.classtracking.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherAttendanceDetailBinding
import com.project.group.rupp.dse.classtracking.models.GetAttendanceDetailAll

class TeacherAttendanceDetailViewHolder(private val binding: ViewHolderTeacherAttendanceDetailBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(data: GetAttendanceDetailAll, position: Int){
        binding.tvNum.text = (position + 1).toString()
        binding.tvName.text = data.name
        if (data.percentage < 60){
            binding.tvScore.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark))
        }else {
            binding.tvScore.setTextColor(ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_onSurface));

        }
        binding.tvScore.text = String.format("%.2f%%", data.percentage)
        binding.tvPresent.text = "present: " + data.present.toString() + "/" + data.total.toString()
    }
}