package com.project.group.rupp.dse.classtracking.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.MainActivity
import com.project.group.rupp.dse.classtracking.databinding.FragementJoinRoomBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.JoinNewRoomViewModel

class JoinRoomFragment: Fragment() {
    private var _binding: FragementJoinRoomBinding? = null
    private val binding get() = _binding!!
    private val joinNewRoomViewModel: JoinNewRoomViewModel by viewModels()

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

        joinNewRoomViewModel.joinNewRoomModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    if (uiState.data!!.status == "error") {
                        when (uiState.data.message) {
                            "Classroom not found" -> {
                                Snackbar.make(binding.root, "Room not found", Snackbar.LENGTH_SHORT)
                                    .show()
                                binding.tfRoomCode.error = "Room not found"
                            }
                            "already join" -> {
                                Snackbar.make(binding.root, "Already join", Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                            "Password is incorrect" -> {
                                binding.llJoinRoomPassword.visibility = View.VISIBLE
                                binding.tfJoinPassword.error = "Password is incorrect"
                            }
                            else -> false
                        }

                    } else if (uiState.data.status == "success") {
                        binding.completeLayout.visibility = View.VISIBLE
                        var description = uiState.data.data!!
                        binding.createRoomCompleteDescription.text = "Room code: ${description.room_code}\nRoom name: ${description.name}\nRoom status: ${description.status}\nRoom password: ${description.password}\n"
                        // set timer
                        val timer = object : CountDownTimer(5000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                            }

                            override fun onFinish() {
                                startActivity(Intent(requireContext(), MainActivity::class.java))
                            }
                        }
                        timer.start()
                    }

                }
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    Snackbar.make(binding.root, uiState.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        binding.btnJoinRoomAfterPassword.setOnClickListener() {
            joinNewRoomViewModel.joinNewRoom(
                requireContext(), roomCode =
                binding.tfRoomCode.text.toString(),
                name = binding.tfNameInRoom.text.toString(),
                password = binding.tfJoinPassword.text.toString())
        }

        binding.btnCreateRoomCompleteHome.setOnClickListener() {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    private fun submit(binding: FragementJoinRoomBinding){
        if (binding.tfRoomCode.text.toString().isEmpty()){
            binding.tfRoomCode.error = "Room code is required"
            return
        }else{
            binding.tfRoomCode.error = null
            joinNewRoomViewModel.joinNewRoom(
                requireContext(), roomCode =
                binding.tfRoomCode.text.toString(),
                name = binding.tfNameInRoom.text.toString(),
                password = binding.tfJoinPassword.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}