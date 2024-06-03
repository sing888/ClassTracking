package com.project.group.rupp.dse.classtracking.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderNewsBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherNews

class TeacherNewsViewHolder (private val binding: ViewHolderNewsBinding) : RecyclerView.ViewHolder(binding.root){

    @SuppressLint("SetTextI18n")
    fun bind(teacherNews: GetTeacherNews){
        binding.tvTitle.text = teacherNews.title
        binding.tvDate.text = teacherNews.post_date.toString() + " - " + teacherNews.deadline.toString()
        binding.tvDescription.text = teacherNews.body
    }

}