package com.project.group.rupp.dse.classtracking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.databinding.FragementJoinRoomBinding

class JoinRoomFragment: Fragment() {
    private var _binding: FragementJoinRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragement_join_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragementJoinRoomBinding.bind(view)

        binding.btnNewRoomSubmit.setOnClickListener() {
            submit(binding)

        }

    }

    private fun submit(binding: FragementJoinRoomBinding){
        if (binding.tfRoomCode.text.toString().isEmpty()){
            binding.tfRoomCode.error = "Room code is required"
            return
        }else{
            binding.tfRoomCode.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}