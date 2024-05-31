package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderNewsBinding

class NewsAdapter: RecyclerView.Adapter<NewsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ViewHolderNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

    }

}