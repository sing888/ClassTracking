package com.project.group.rupp.dse.classtracking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.project.group.rupp.dse.classtracking.databinding.ViewHolderRoomBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.viewmodels.RoomViewModel

class RoomAdapter: RecyclerView.Adapter<RoomViewHolder>() {
    private var dataset: List<GetRoom> = listOf()
    private var listener: RecyclerListener? = null
    private var roomViewModel: RoomViewModel? = null

    public fun setDataset(data: List<GetRoom>) {
        dataset = data
        notifyDataSetChanged()
    }

    public fun setListener(listener: RecyclerListener) {
        this.listener = listener
    }

    public fun setRoomViewModel(roomViewModel: RoomViewModel) {
        this.roomViewModel = roomViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ViewHolderRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(dataset[position], position, roomViewModel)

        holder.itemView.setOnClickListener {
            listener?.onViewRecycled(holder)
        }
    }

}