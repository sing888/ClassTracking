package com.project.group.rupp.dse.classtracking.fragment

import android.os.Bundle
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
                }
                UiStateStatus.success -> {
                    binding.progressLayoutAchieve.visibility = View.GONE
                    data = uiState.data!!.data!!
                    if (data != null) {
                        adapter.setDataset(data)
                    }

                }
                UiStateStatus.error -> {
                    binding.progressLayoutAchieve.visibility = View.GONE
                    Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(requireContext(), "Room_id: ${room.classroom_id}", Toast.LENGTH_SHORT).show()
        }

    }
}
