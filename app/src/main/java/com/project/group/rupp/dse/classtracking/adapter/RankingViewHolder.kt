package com.project.group.rupp.dse.classtracking.adapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderRankingBinding
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreList

class RankingViewHolder(private val binding: ViewHolderRankingBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(rank: GetStudentScoreList, position: Int, member_id: String){
        binding.tvName.text = rank.member_name
        binding.tvNum.text = (position + 1).toString()
        binding.tvScore.text = rank.average_score.toString() + " >"

        if (rank.member_id == member_id) {
            binding.attendanceStudentCardView.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, R.color.md_theme_primaryContainer)
            binding.tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_surface))
            binding.tvNum.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_surface))
            binding.tvScore.setTextColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_surface))
        }
    }
}