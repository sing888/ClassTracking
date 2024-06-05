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
import com.project.group.rupp.dse.classtracking.viewmodels.TeacherScoreViewModel

class SubjectViewHolder(private val binding: ViewHolderScoreTeacherBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(subject: GetTeacherSubject, subjectViewHolder: TeacherScoreViewModel){
        binding.subName.text = subject.name
        binding.subMore.setOnClickListener {
            showMenu(it.context, it, R.menu.room_more, subjectViewHolder, subject.subject_id)
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, subjectViewHolder: TeacherScoreViewModel, subjectId: String? = null) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit -> {
                    Snackbar.make(v, "Edit room", Snackbar.LENGTH_SHORT).show()
                    true
                }

                R.id.menu_delete -> {
                    subjectViewHolder.deleteSubject(context, subjectId!!)
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