package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAchieveBinding
import com.project.group.rupp.dse.classtracking.models.GetAchieve

class AchieveViewHolder(private val binding: ViewHolderAchieveBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(room: GetAchieve) {
        binding.roomName.text = room.name
        binding.roomDescription.text = room.room_code
    }
}
