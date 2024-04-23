package com.project.group.rupp.dse.classtracking.fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.FragmentRoomBinding

class RoomFragment : Fragment() {
    private var _binding: FragmentRoomBinding? = null
    private val binding get() = _binding!!

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

        binding.btnRoomStudent.setOnClickListener {
            binding.btnRoomStudent.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1E8D6")));
            binding.btnRoomTeacher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
        }

        binding.btnRoomTeacher.setOnClickListener {
            binding.btnRoomTeacher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD1E8D6")));
            binding.btnRoomStudent.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
        }


    }
}