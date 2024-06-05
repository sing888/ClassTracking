package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderScoreTeacherBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherSubject
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherScoreViewModel

class SubjectAdapter: RecyclerView.Adapter<SubjectViewHolder>() {
    private var dataset: List<GetTeacherSubject> = listOf()
    private var listener: RecyclerListener? = null
    private var subjectViewHolder : TeacherScoreViewModel ?= null

    fun setViewModel(viewModel: TeacherScoreViewModel){
        subjectViewHolder = viewModel
    }

    fun setDataset(data: List<GetTeacherSubject>){
        dataset = data
        notifyDataSetChanged()
    }

    public fun setListener(listener: RecyclerListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ViewHolderScoreTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(dataset[position], subjectViewHolder!!)

        holder.itemView.setOnClickListener{
            listener?.onViewRecycled(holder)
        }

    }
}