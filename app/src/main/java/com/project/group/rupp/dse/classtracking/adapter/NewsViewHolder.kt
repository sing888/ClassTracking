package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderNewsBinding

class NewsViewHolder(private val binding: ViewHolderNewsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(news: String){
//            binding.tvTitle.text = news
        }
}