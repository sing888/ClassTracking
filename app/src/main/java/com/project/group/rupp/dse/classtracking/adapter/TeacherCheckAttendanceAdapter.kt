package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderCheckAttendanceBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance

class TeacherCheckAttendanceAdapter: RecyclerView.Adapter<TeacherCheckAttendanceViewHolder>(){
    private var dataset: List<GetTeacherAttendance> = listOf()

    fun setDataset(data: List<GetTeacherAttendance>){
        dataset = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeacherCheckAttendanceViewHolder {
        val binding = ViewHolderCheckAttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherCheckAttendanceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TeacherCheckAttendanceViewHolder, position: Int) {
        holder.bind(dataset[position], position)
    }

}