package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderScoreStudentDetailBinding
import com.project.group.rupp.dse.classtracking.models.SubjectScore

class StudentScoreDetailSubjectScoreAdapter: RecyclerView.Adapter<StudentScoreDetailSubjectScoreViewHolder>() {
    private var subject_socre: List<SubjectScore> = listOf()

    fun setSubjectScore(subject_score: List<SubjectScore>){
        this.subject_socre = subject_score
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentScoreDetailSubjectScoreViewHolder {
        val binding = ViewHolderScoreStudentDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentScoreDetailSubjectScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subject_socre.size
    }

    override fun onBindViewHolder(holder: StudentScoreDetailSubjectScoreViewHolder, position: Int) {
        holder.bind(subject_socre[position])
    }
}