package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderMemberBinding
import com.project.group.rupp.dse.classtracking.models.GetMember

class MemberAdapter:RecyclerView.Adapter<MemberViewHolder>() {
    private var dataset: List<GetMember> = listOf()
    fun setDataset(data: List<GetMember>) {
        dataset = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
       val binding = ViewHolderMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(dataset[position], position)
    }

}