package com.project.group.rupp.dse.classtracking.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.MenuRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.CreateNewRoomActivity
import com.project.group.rupp.dse.classtracking.activity.RoomActivity
import com.project.group.rupp.dse.classtracking.adapter.RoomAdapter
import com.project.group.rupp.dse.classtracking.databinding.FragmentRoomBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.MainViewModel
import com.project.group.rupp.dse.classtracking.viewmodels.RoomViewModel

class RoomFragment : Fragment() {
    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

    private val roomViewModel: RoomViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var account_id: String = ""

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

        var search = false
        var room : GetRoom? = null
        var data = listOf<GetRoom>()
        val adapter = RoomAdapter()
        var roomtype = "student"
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        roomViewModel.getStudentRooms(requireContext())
        adapter.setRoomViewModel(roomViewModel)

        val searchView: SearchView = binding.searchViewRoom

        searchView.setOnSearchClickListener {
            binding.appName.visibility = View.GONE
            binding.headerSpace.visibility = View.GONE
            binding.toggleButton.visibility = View.GONE
            search = true
            searchView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        }

        searchView.setOnCloseListener {
            binding.appName.visibility = View.VISIBLE
            binding.headerSpace.visibility = View.VISIBLE
            searchView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.toggleButton.visibility = View.VISIBLE
            search = false
            roomViewModel.getStudentRooms(requireContext())
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Perform search using the query
                    performSearch(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // Update search results
                    updateSearchResults(it)
                }
                return false
            }
        })

        binding.btnRoomStudent.setOnClickListener {
            roomtype = "student"
            binding.btnRoomStudent.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.md_theme_secondaryContainer))
            binding.btnRoomTeacher.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.md_theme_surface));

            roomViewModel.getStudentRooms(requireContext())
        }

        binding.btnRoomTeacher.setOnClickListener {
            roomtype = "teacher"
            binding.btnRoomTeacher.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.md_theme_secondaryContainer));
            binding.btnRoomStudent.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.md_theme_surface));

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
                        binding.recyclerViewRoom.visibility = View.GONE
                        binding.textViewRoom.text = "No room found"
                    } else {
                        adapter.setDataset(data)
                        binding.recyclerViewRoom.visibility = View.VISIBLE
                        binding.recyclerViewRoom.adapter = adapter
                        binding.recyclerViewRoom.layoutManager = layoutManager
                    }
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.textViewRoom.visibility = View.VISIBLE
                    binding.textViewRoom.text = "Something went wrong!"
                    binding.recyclerViewRoom.visibility = View.GONE
                }
            }
        })

        roomViewModel.searchModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
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
                        binding.recyclerViewRoom.visibility = View.GONE
                    } else {
                        adapter.setDataset(data)
                        binding.recyclerViewRoom.visibility = View.VISIBLE
                        binding.recyclerViewRoom.adapter = adapter
                        binding.recyclerViewRoom.layoutManager = layoutManager
                    }
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.textViewRoom.visibility = View.VISIBLE
                    binding.textViewRoom.text = "Something went wrong!"
                    binding.recyclerViewRoom.visibility = View.GONE
                }
            }
        })

        roomViewModel.achieveModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                    binding.textViewRoom.visibility = View.GONE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    if (roomtype == "teacher")
                        roomViewModel.getTeacherRooms(requireContext())
                    else
                        roomViewModel.getStudentRooms(requireContext())
                    Snackbar.make(view, "Achieve success", Snackbar.LENGTH_SHORT).show()
                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    Snackbar.make(view, "Achieve failed", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        roomViewModel.roleModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                }
                UiStateStatus.success -> {
                    val role = uiState.data?.data
                    val intent = Intent(requireContext(), RoomActivity::class.java)
                    intent.putExtra("room_id", room!!.classroom_id)
                    intent.putExtra("room_name", room!!.name)
                    intent.putExtra("room_description", room!!.room_code)
                    intent.putExtra("room_password", room!!.password)
                    intent.putExtra("room_type", role)
                    intent.putExtra("account_id", account_id)
                    startActivity(intent)
                }
                UiStateStatus.error -> {
                    Snackbar.make(view, "Something went wrong!", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        adapter.setListener{ index: RecyclerView.ViewHolder? ->
            room = data[index!!.adapterPosition]
            if (search) {
                roomViewModel.getRole(requireContext(), room!!.classroom_id)
            }else {
                // give text to next screen with set extra
                val intent: Intent = Intent(requireContext(), RoomActivity::class.java)
                intent.putExtra("room_id", room!!.classroom_id)
                intent.putExtra("room_name", room!!.name)
                intent.putExtra("room_description", room!!.room_code)
                intent.putExtra("room_password", room!!.password)
                intent.putExtra("room_type", roomtype)
                intent.putExtra("account_id", account_id)
                startActivity(intent)
            }
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
            val intent = Intent(requireContext(), CreateNewRoomActivity::class.java)
            intent.putExtra("header", "Create New Room")
            startActivity(intent)
        }

        binding.btnJoinNewRoom.setOnClickListener {
            val intent = Intent(requireContext(), CreateNewRoomActivity::class.java)
            intent.putExtra("header", "Join New Room")
            startActivity(intent)
        }

        mainViewModel.account_id.observe(viewLifecycleOwner, Observer {
            account_id = it
        })

    }
    private fun performSearch(query: String) {
        // Implement your search logic here
        roomViewModel.getSearch(requireContext(), query)

    }

    private fun updateSearchResults(newText: String) {


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

