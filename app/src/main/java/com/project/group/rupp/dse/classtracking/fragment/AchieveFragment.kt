package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.RoomActivity
import com.project.group.rupp.dse.classtracking.adapter.AchieveAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentAchieveBinding
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.AchieveViewModel

class AchieveFragment: Fragment() {
    private var _binding: FragmentAchieveBinding? = null
    private val binding get() = _binding!!

    private val achieveViewModel: AchieveViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achieve, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAchieveBinding.bind(view)

        var data = listOf<GetAchieve>()
        var adapter: AchieveAdapter = AchieveAdapter()

        achieveViewModel.achieveModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.progressLayoutAchieve.visibility = View.VISIBLE
                    binding.noAchieveClassroomFound.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.progressLayoutAchieve.visibility = View.GONE
                    data = uiState.data!!.data!!
                    if (data.isEmpty()) {
                        binding.noAchieveClassroomFound.visibility = View.VISIBLE
                        binding.noAchieveClassroomFound.textAlignment = View.TEXT_ALIGNMENT_CENTER
                        binding.noAchieveClassroomFound.text = "No achieve classroom found"
                    } else{
                        binding.noAchieveClassroomFound.visibility = View.VISIBLE
                        binding.noAchieveClassroomFound.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                        adapter.setDataset(data)
                    }

                }
                UiStateStatus.error -> {
                    binding.progressLayoutAchieve.visibility = View.GONE
                    Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
                    binding.noAchieveClassroomFound.visibility = View.VISIBLE
                    binding.noAchieveClassroomFound.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    binding.noAchieveClassroomFound.text = "Something went wrong!"
                }
            }
        })

        achieveViewModel.getRoom(this.requireContext())

        // layout manager
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

        // recycler view
        binding.recyclerViewAchieve.layoutManager = layoutManager
        binding.recyclerViewAchieve.adapter = adapter

        adapter.setListener { index: RecyclerView.ViewHolder? ->
            var room = data[index!!.adapterPosition]
//            Toast.makeText(requireContext(), "Room_id: ${room.classroom_id}", Toast.LENGTH_SHORT).show()
            var intent: Intent = Intent(this.requireContext(), RoomActivity::class.java)
            intent.putExtra("room_type", "achieve")
            intent.putExtra("room_id", room.classroom_id)
            intent.putExtra("room_name", room.name)
            intent.putExtra("room_code", room.room_code)

            startActivity(intent)

        }

    }
}
