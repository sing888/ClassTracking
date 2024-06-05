package com.project.group.rupp.dse.classtracking.adapter

import android.content.Context
import android.support.annotation.MenuRes
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderExamBinding
import com.project.group.rupp.dse.classtracking.models.GetExam
import com.project.group.rupp.dse.classtracking.viewmodels.ExamViewModel

class ExamViewHolder(private val binding: ViewHolderExamBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GetExam, examViewModel: ExamViewModel?) {
        binding.examName.text = data.name
        binding.examScore.text = data.max_score.toString()
        binding.examMore.setOnClickListener {
            showMenu(it.context, it, R.menu.room_more, examViewModel, data)
        }
    }

    private fun showMenu(context: Context, v: View, @MenuRes menuRes: Int, examViewModel: ExamViewModel?, data: GetExam) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_edit -> {
                    Snackbar.make(v, "Edit room", Snackbar.LENGTH_SHORT).show()
                    true
                }

                R.id.menu_delete -> {
                    examViewModel?.deleteExam(context, data.exam_id)
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