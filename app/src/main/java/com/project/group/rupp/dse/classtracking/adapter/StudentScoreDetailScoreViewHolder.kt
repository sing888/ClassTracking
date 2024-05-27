package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderScoreStudentDetailScoreBinding
import com.project.group.rupp.dse.classtracking.models.Score

class StudentScoreDetailScoreViewHolder(private val binding: ViewHolderScoreStudentDetailScoreBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Score){
            binding.tvSubject.text = data.subject
            binding.tvScore.text = data.score.toString()
            binding.tvQuiz.text = data.exam.toString()
        }
}