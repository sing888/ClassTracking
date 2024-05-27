package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderScoreStudentDetailBinding
import com.project.group.rupp.dse.classtracking.models.SubjectScore

class StudentScoreDetailSubjectScoreViewHolder(private val binding: ViewHolderScoreStudentDetailBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(data: SubjectScore){
        binding.tvSubject.text = data.subject_name
        binding.tvScore.text = data.score.toString()
    }
}