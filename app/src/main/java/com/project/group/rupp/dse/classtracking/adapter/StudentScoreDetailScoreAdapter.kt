package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderScoreStudentDetailScoreBinding
import com.project.group.rupp.dse.classtracking.models.Score

class StudentScoreDetailScoreAdapter: RecyclerView.Adapter<StudentScoreDetailScoreViewHolder>(){
    private var score: List<Score> = listOf()
    fun setScore(score: List<Score>){
        this.score = score
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentScoreDetailScoreViewHolder {
        val binding = ViewHolderScoreStudentDetailScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentScoreDetailScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return score.size
    }

    override fun onBindViewHolder(holder: StudentScoreDetailScoreViewHolder, position: Int) {
        holder.bind(score[position])
    }

}