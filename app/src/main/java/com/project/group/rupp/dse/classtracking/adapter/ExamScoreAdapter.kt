package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderTeacherExamScoreBinding
import com.project.group.rupp.dse.classtracking.models.GetExamScoreDetail

class ExamScoreAdapter: RecyclerView.Adapter<ExamScoreViewHolder>() {
    private var dataset: List<GetExamScoreDetail> = listOf()
    private var listener: RecyclerListener? = null

    fun setData(data: List<GetExamScoreDetail>){
        dataset = data
        notifyDataSetChanged()
    }

    fun setListener(listener: RecyclerListener){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamScoreViewHolder {
        val binding = ViewHolderTeacherExamScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExamScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ExamScoreViewHolder, position: Int) {
        holder.bind(dataset[position], position)

        holder.itemView.setOnClickListener {
            listener?.onViewRecycled(holder)
        }
    }
}