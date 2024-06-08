package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderNewsBinding
import com.project.group.rupp.dse.classtracking.models.GetNews

class NewsAdapter: RecyclerView.Adapter<NewsViewHolder>() {
    private var dataset: List<GetNews> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ViewHolderNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    public fun setDataset(data: List<GetNews>){
        dataset = data
        notifyDataSetChanged()
    }


}