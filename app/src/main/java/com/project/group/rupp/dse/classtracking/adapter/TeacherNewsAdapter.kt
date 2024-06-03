package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderNewsBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherNews

class TeacherNewsAdapter: RecyclerView.Adapter<TeacherNewsViewHolder>() {
    private var dataset: List<GetTeacherNews> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherNewsViewHolder {
        val binding = ViewHolderNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherNewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TeacherNewsViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    public fun setDataset(data: List<GetTeacherNews>){
        dataset = data
        notifyDataSetChanged()
    }


}