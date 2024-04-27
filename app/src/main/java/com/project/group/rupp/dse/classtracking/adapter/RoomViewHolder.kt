package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderRoomBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom

class RoomViewHolder(private val binding: ViewHolderRoomBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(room: GetRoom){
        binding.roomName.text = room.name
        binding.roomDescription.text = room.room_code
        binding.roomMore.setOnClickListener {
            binding.roomMoreMenu.visibility = if (binding.roomMoreMenu.visibility == RecyclerView.VISIBLE) RecyclerView.GONE else RecyclerView.VISIBLE

            binding.roomMoreMenuItem.setNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_edit -> {
                        Snackbar.make(binding.root, "Edit room", Snackbar.LENGTH_SHORT).show()
                        true
                    }

                    R.id.menu_delete -> {
                        Snackbar.make(binding.root, "Delete room", Snackbar.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }
        }
    }
}