package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderExamBinding
import com.project.group.rupp.dse.classtracking.models.GetExam

class ExamViewHolder(private val binding: ViewHolderExamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetExam) {
        binding.examName.text = data.name
    }
}