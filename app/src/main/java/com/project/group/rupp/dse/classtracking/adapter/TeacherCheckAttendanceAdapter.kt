package com.project.group.rupp.dse.classtracking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderCheckAttendanceBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherAttendanceViewModel

class TeacherCheckAttendanceAdapter: RecyclerView.Adapter<TeacherCheckAttendanceViewHolder>(){
    private var dataset: List<GetTeacherAttendance> = listOf()
    private var classroomId: String = ""
    private var date = ""
    private var viewmodel: TeacherAttendanceViewModel? = null
    private var context: Context? = null

    fun setContext(context: Context){
        this.context = context
    }

    fun setViewModel(viewmodel: TeacherAttendanceViewModel){
        this.viewmodel = viewmodel
    }

    fun setDate(date: String){
        this.date = date
    }

    fun setClassroomId(id: String){
        classroomId = id
    }

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
        holder.bind(dataset[position], position, classroomId, date, viewmodel!!, context!!)
    }

}