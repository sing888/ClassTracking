package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
    ): View {
        return FragmentAchieveBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAchieveBinding.bind(view)

        var data = listOf<GetAchieve>()
        val adapter= AchieveAdapter()
        // layout manager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter.setAchieveViewModel(achieveViewModel)

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

                        // recycler view
                        binding.recyclerViewAchieve.layoutManager = layoutManager
                        binding.recyclerViewAchieve.adapter = adapter
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

        achieveViewModel.unachieveModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                }
                UiStateStatus.success -> {
                    achieveViewModel.getRoom(this.requireContext())
                    Snackbar.make(view, "Unachieve success", Snackbar.LENGTH_SHORT).show()
                }
                UiStateStatus.error -> {
                    Snackbar.make(view, "Unachieve failed", Snackbar.LENGTH_SHORT).show()
                }
            }
        })


        var room : GetAchieve ? = null

        adapter.setListener { index: RecyclerView.ViewHolder? ->
            room = data[index!!.adapterPosition]
            achieveViewModel.getRole(this.requireContext(), room!!.classroom_id)

        }

        achieveViewModel.roleModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                }
                UiStateStatus.success -> {
                    binding.progressLayoutAchieve.visibility = View.GONE
                    val intent: Intent = Intent(this.requireContext(), RoomActivity::class.java)
                    intent.putExtra("room_id", room!!.classroom_id)
                    intent.putExtra("room_name", room!!.name)
                    intent.putExtra("room_code", room!!.room_code)
                    intent.putExtra("room_password", "")
                    intent.putExtra("account_id", "")
                    intent.putExtra("room_type", uiState.data!!.data!!)

                    startActivity(intent)
                }
                UiStateStatus.error -> {
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
