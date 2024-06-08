package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAchieveBinding
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.viewmodels.AchieveViewModel

class AchieveAdapter : RecyclerView.Adapter<AchieveViewHolder>() {
    private var dataset: List<GetAchieve> = listOf()
    private var listener: RecyclerListener? = null
    private var achieveViewModel: AchieveViewModel? = null

    public fun setDataset(data: List<GetAchieve>) {
        dataset = data
        notifyDataSetChanged()
    }

    public fun setListener(listener: RecyclerListener) {
        this.listener = listener
    }

    public fun setAchieveViewModel(achieveViewModel: AchieveViewModel) {
        this.achieveViewModel = achieveViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
        val binding = ViewHolderAchieveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchieveViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
        holder.bind(dataset[position], achieveViewModel)

        holder.itemView.setOnClickListener {
            listener?.onViewRecycled(holder)
        }
    }
}
