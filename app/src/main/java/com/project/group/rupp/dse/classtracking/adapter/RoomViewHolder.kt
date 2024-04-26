package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderRoomBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom

class RoomViewHolder(private val binding: ViewHolderRoomBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(room: GetRoom){
        binding.roomName.text = room.name
        binding.roomDescription.text = room.room_code
    }
}