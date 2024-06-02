package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherAttendanceDetailBinding
import com.project.group.rupp.dse.classtracking.models.GetAttendanceDetailAll

class TeacherAttendanceDetailAdapter: RecyclerView.Adapter<TeacherAttendanceDetailViewHolder>() {
    private var dataset: List<GetAttendanceDetailAll> = listOf()

    fun setDataset(data: List<GetAttendanceDetailAll>) {
        dataset = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherAttendanceDetailViewHolder {
        val binding = ViewHolderTeacherAttendanceDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherAttendanceDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TeacherAttendanceDetailViewHolder, position: Int) {
        holder.bind(dataset[position], position)
    }
}