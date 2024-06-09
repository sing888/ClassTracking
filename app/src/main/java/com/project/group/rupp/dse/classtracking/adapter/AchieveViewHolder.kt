package com.project.group.rupp.dse.classtracking.adapter

import android.content.Context
import android.support.annotation.MenuRes
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderAchieveBinding
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.viewmodels.AchieveViewModel

class AchieveViewHolder(private val binding: ViewHolderAchieveBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(room: GetAchieve, position: Int ,achieveViewModel: AchieveViewModel?) {
        if (room.status == "Public" || room.status == "public") {
            binding.roomNum.text = "+"
        }else if (room.status == "Private" || room.status == "private") {
            binding.roomNum.text = "-"
        }
        else {
            binding.roomNum.text = "o"
        }
        binding.roomName.text = room.name
        binding.roomDescription.text = room.room_code

        binding.roomMore.setOnClickListener {
            showMenu(it.context, it, R.menu.achieve_more, achieveViewModel, room)
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, achieveViewModel: AchieveViewModel?, room: GetAchieve) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_unachieve -> {
                    achieveViewModel?.getUnachieve(context, room.classroom_id)
                    true
                }

                else -> false
            }
        }
        popup.setOnDismissListener {

        }
        // Show the popup menu.
        popup.show()
    }
}
