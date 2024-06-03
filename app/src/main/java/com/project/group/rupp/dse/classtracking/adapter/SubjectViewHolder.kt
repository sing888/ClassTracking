package com.project.group.rupp.dse.classtracking.adapter

import android.content.Context
import android.support.annotation.MenuRes
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderScoreTeacherBinding
import com.project.group.rupp.dse.classtracking.models.GetTeacherSubject

class SubjectViewHolder(private val binding: ViewHolderScoreTeacherBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(subject: GetTeacherSubject){
        binding.subName.text = subject.name
        binding.subMore.setOnClickListener {
            showMenu(it.context, it, R.menu.room_more)
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit -> {
                    Snackbar.make(v, "Edit room", Snackbar.LENGTH_SHORT).show()
                    true
                }

                R.id.menu_delete -> {
                    Snackbar.make(v, "Delete room", Snackbar.LENGTH_SHORT).show()
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