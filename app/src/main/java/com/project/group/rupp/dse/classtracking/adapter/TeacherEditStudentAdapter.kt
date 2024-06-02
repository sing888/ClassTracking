package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherAttendanceEditStudentBinding
import com.project.group.rupp.dse.classtracking.models.GetMember
import com.project.group.rupp.dse.classtracking.viewmodels.MemberViewModel

class TeacherEditStudentAdapter: RecyclerView.Adapter<TeacherEditStudentViewHolder>(){
    private var dataset: List<GetMember> = listOf()
    private var viewModel: MemberViewModel? = null

    fun setViewModel(viewModel: MemberViewModel){
        this.viewModel = viewModel
    }

    fun setDataset(data: List<GetMember>){
        dataset = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherEditStudentViewHolder {
        val binding = ViewHolderTeacherAttendanceEditStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherEditStudentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TeacherEditStudentViewHolder, position: Int) {
        holder.bind(dataset[position], position, viewModel!!)
    }
}