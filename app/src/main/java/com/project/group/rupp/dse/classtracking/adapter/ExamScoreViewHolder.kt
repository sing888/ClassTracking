package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherExamScoreBinding
import com.project.group.rupp.dse.classtracking.models.GetExamScoreDetail

class ExamScoreViewHolder(private val binding: ViewHolderTeacherExamScoreBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetExamScoreDetail, position: Int) {
        binding.tvNum.text = (position + 1).toString()
        binding.tvName.text = data.name
        binding.tvMaxScore.text = data.max_score
        if (data.score != null){
            binding.tvScore.text = data.score
        }else{
            binding.tvScore.text = "N/A"
        }
    }

}