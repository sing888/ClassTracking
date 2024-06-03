package com.project.group.rupp.dse.classtracking.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherAttendanceEditStudentBinding
import com.project.group.rupp.dse.classtracking.models.GetMember
import com.project.group.rupp.dse.classtracking.viewmodels.MemberViewModel

class TeacherEditStudentViewHolder(private val binding: ViewHolderTeacherAttendanceEditStudentBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetMember, position: Int, viewModel: MemberViewModel){
        if (data.role == "teacher"){
            binding.ivDelete.visibility = View.GONE
        }
        binding.tvNum.text = (position + 1).toString()
        binding.tvName.text = data.name
        binding.ivDelete.setOnClickListener {
            viewModel.deleteMember(binding.root.context, data.member_id)
        }
    }
}