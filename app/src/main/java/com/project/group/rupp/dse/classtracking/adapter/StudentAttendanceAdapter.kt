package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAttendanceBinding
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendanceDetail

class StudentAttendanceAdapter: RecyclerView.Adapter<StudentAttendanceViewHolder>() {
    private var dataset: List<GetStudentAttendanceDetail> = listOf()

    fun setDataset(data: List<GetStudentAttendanceDetail>) {
        dataset = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAttendanceViewHolder {
        var binding = ViewHolderAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentAttendanceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: StudentAttendanceViewHolder, position: Int) {
        holder.bind(dataset[position])
    }
}