package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.style.TextIndent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.CreateNewRoomActivity
import com.project.group.rupp.dse.classtracking.activity.RoomActivity
import com.project.group.rupp.dse.classtracking.adapter.RoomAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentRoomBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.RoomViewModel

class RoomFragment : Fragment() {
    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private val roomViewModel: RoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomBinding.bind(view)

        var data = listOf<GetRoom>()
        var adapter = RoomAdapter()
        var roomtype = "student"

        roomViewModel.getStudentRooms(requireContext())

        binding.btnRoomStudent.setOnClickListener {
            roomtype = "student"
            binding.btnRoomStudent.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1E8D6")));
            binding.btnRoomTeacher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

            roomViewModel.getStudentRooms(requireContext())
        }

        binding.btnRoomTeacher.setOnClickListener {
            roomtype = "teacher"
            binding.btnRoomTeacher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1E8D6")));
            binding.btnRoomStudent.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

            roomViewModel.getTeacherRooms(requireContext())
        }

        roomViewModel.roomModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                    binding.textViewRoom.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    data = uiState.data?.data!!
                    if (data.isEmpty()) {
                        binding.textViewRoom.visibility = View.VISIBLE
                        binding.textViewRoom.text = "No room found"
                    } else {
                        adapter.setDataset(data)
                    }
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.textViewRoom.visibility = View.VISIBLE
                    binding.textViewRoom.text = "Something went wrong!"
                }
            }
        })

        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewRoom.layoutManager = layoutManager
        binding.recyclerViewRoom.adapter = adapter

        adapter.setListener{ index: RecyclerView.ViewHolder? ->
            var room = data[index!!.adapterPosition]
            // give text to next screen with setextra
            var intent: Intent = Intent(requireContext(), RoomActivity::class.java)
            intent.putExtra("room_id", room.classroom_id)
            intent.putExtra("room_name", room.name)
            intent.putExtra("room_description", room.room_code)
            intent.putExtra("room_password", room.password)
            intent.putExtra("room_type", roomtype)
            startActivity(intent)
        }

        binding.floatingActionButton.setOnClickListener {
            when(binding.floatingButtonMenuLayout.visibility){
                View.VISIBLE -> {
                    binding.floatingButtonMenuLayout.visibility = View.GONE
                    binding.floatingActionButton.setImageResource(R.drawable.ic_add)
                }
                View.GONE -> {
                    binding.floatingButtonMenuLayout.visibility = View.VISIBLE
                    binding.floatingActionButton.setImageResource(R.drawable.ic_x)
                }
            }
        }

        binding.floatingButtonMenuLayout.setOnClickListener {
            binding.floatingButtonMenuLayout.visibility = View.GONE
            binding.floatingActionButton.setImageResource(R.drawable.ic_add)
        }

        binding.btnCreateNewRoom.setOnClickListener {
            var intent = Intent(requireContext(), CreateNewRoomActivity::class.java)
            intent.putExtra("header", "Create New Room")
            startActivity(intent)
        }

        binding.btnJoinNewRoom.setOnClickListener {
            var intent = Intent(requireContext(), CreateNewRoomActivity::class.java)
            intent.putExtra("header", "Join New Room")
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

