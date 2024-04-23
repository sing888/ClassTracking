package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAchieveBinding
import com.project.group.rupp.dse.classtracking.models.GetAchieve

class AchieveAdapter : RecyclerView.Adapter<AchieveViewHolder>() {
    private var dataset: List<GetAchieve> = listOf()
    private var listener: RecyclerListener? = null

    public fun setDataset(data: List<GetAchieve>) {
        dataset = data
        notifyDataSetChanged()
    }

    public fun setListener(listener: RecyclerListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
        val binding = ViewHolderAchieveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AchieveViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
        holder.bind(dataset[position])

        holder.itemView.setOnClickListener {
            listener?.onViewRecycled(holder)
        }
    }
}
