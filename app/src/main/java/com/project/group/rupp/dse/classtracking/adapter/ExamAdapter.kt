package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderExamBinding
import com.project.group.rupp.dse.classtracking.models.GetExam
import com.project.group.rupp.dse.classtracking.viewmodels.ExamViewModel

class ExamAdapter: RecyclerView.Adapter<ExamViewHolder>() {
    private var dataset : List<GetExam> = listOf()
    private var listener: RecyclerListener? = null
    private var examViewModel : ExamViewModel ? = null

    fun setData(data: List<GetExam>) {
        dataset = data
        notifyDataSetChanged()
    }
    fun setListener(listener: RecyclerListener) {
        this.listener = listener
    }

    fun setViewModel(viewModel: ExamViewModel) {
        examViewModel = viewModel
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val binding = ViewHolderExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(dataset[position], examViewModel)
        holder.itemView.setOnClickListener {
            listener?.onViewRecycled(holder)
        }
    }
}