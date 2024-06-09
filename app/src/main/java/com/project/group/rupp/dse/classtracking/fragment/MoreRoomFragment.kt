package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.ClassroomSettingActivity
import com.project.group.rupp.dse.classtracking.activity.MemberActivity
import com.project.group.rupp.dse.classtracking.databinding.FragmentRoomMoreBinding
import com.project.group.rupp.dse.classtracking.viewmodels.RoomMainViewModel

class MoreRoomFragment: Fragment() {
    private var __binding: FragmentRoomMoreBinding? = null
    private val binding get() = __binding!!
    private val roomMainViewModel: RoomMainViewModel by activityViewModels()
    private var room_id = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRoomMoreBinding.inflate(inflater, container, false).apply {
            __binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMember.setOnClickListener {
            val intent: Intent = Intent(requireContext(), MemberActivity::class.java)
            intent.putExtra("room_id", room_id)
            startActivity(intent)
        }

        roomMainViewModel.roomId.observe(viewLifecycleOwner, Observer {
            room_id = it
        })

        if (roomMainViewModel.roomType.value == "teacher") {
            binding.btnSetting.visibility = View.VISIBLE
        } else {
            binding.btnSetting.visibility = View.GONE
        }

        binding.btnSetting.setOnClickListener {
            val intent: Intent = Intent(requireContext(), ClassroomSettingActivity::class.java)
            intent.putExtra("room_id", room_id)
            intent.putExtra("room_name", roomMainViewModel.roomName.value)
            intent.putExtra("room_type", roomMainViewModel.roomType.value)
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        __binding = null
    }

}