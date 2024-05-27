package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderRankingBinding
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreList

class RankingAdapter: RecyclerView.Adapter<RankingViewHolder>() {
    private var dataset: List<GetStudentScoreList> = listOf()
    private var listener: RecyclerView.RecyclerListener? = null
    private var member_id: String = ""

    public fun setListener(listener: RecyclerView.RecyclerListener) {
        this.listener = listener
    }

    fun setMemberId(member_id: String) {
        this.member_id = member_id
    }
    fun setData(data: List<GetStudentScoreList>) {
        dataset = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = ViewHolderRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(dataset[position], position, member_id)

        if (dataset[position].member_id == member_id) {
            holder.itemView.setOnClickListener {
                listener?.onViewRecycled(holder)
            }
        }
    }

}