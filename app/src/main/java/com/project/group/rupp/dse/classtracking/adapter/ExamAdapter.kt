package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderExamBinding
import com.project.group.rupp.dse.classtracking.models.GetExam

class ExamAdapter: RecyclerView.Adapter<ExamViewHolder>() {
    private var dataset : List<GetExam> = listOf()
    fun setData(data: List<GetExam>) {
        dataset = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val binding = ViewHolderExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(dataset[position])
    }
}