package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderMemberBinding
import com.project.group.rupp.dse.classtracking.models.GetMember

class MemberViewHolder(private val binding: ViewHolderMemberBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(member: GetMember, position: Int) {
        binding.tvName.text = member.name
        binding.tvNum.text = (position + 1).toString()
        binding.tvRole.text = member.role
    }
}