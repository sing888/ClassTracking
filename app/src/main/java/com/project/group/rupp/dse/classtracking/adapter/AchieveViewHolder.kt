package com.project.group.rupp.dse.classtracking.adapter

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAchieveBinding
import com.project.group.rupp.dse.classtracking.models.GetAchieve

class AchieveViewHolder(private val binding: ViewHolderAchieveBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(room: GetAchieve) {
        binding.roomName.text = room.name
        binding.roomDescription.text = room.room_code

        binding.roomMore.setOnClickListener {
            binding.achieveMoreMenu.visibility = if
                    (binding.achieveMoreMenu.visibility == RecyclerView.VISIBLE)
                    RecyclerView.GONE else RecyclerView.VISIBLE

            binding.achieveMoreMenuItem.setNavigationItemSelectedListener { item ->
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
