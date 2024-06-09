package com.project.group.rupp.dse.classtracking.adapter
import android.content.Context
import android.support.annotation.MenuRes
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderRoomBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.viewmodels.RoomViewModel

class RoomViewHolder(private val binding: ViewHolderRoomBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(room: GetRoom, position: Int , roomViewModel: RoomViewModel?){
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
            showMenu(it.context, it, R.menu.room_more, roomViewModel, room)
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, roomViewModel: RoomViewModel?, data: GetRoom) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_achieve -> {
                    roomViewModel?.getMakeAchieve(context, data.classroom_id)
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