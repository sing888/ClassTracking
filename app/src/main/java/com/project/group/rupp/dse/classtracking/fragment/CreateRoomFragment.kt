package com.project.group.rupp.dse.classtracking.fragment
import PreferenceUtils
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.R
import com.project.group.rupp.dse.classtracking.activity.MainActivity
import com.project.group.rupp.dse.classtracking.databinding.FragementCreateRoomBinding
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.CreateRoomViewModel

class CreateRoomFragment: Fragment(){
    private var _binding: FragementCreateRoomBinding? = null
    private val binding get() = _binding!!
    private val createRoomViewModel: CreateRoomViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragement_create_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragementCreateRoomBinding.bind(view)


        // dropdown status
        val statusOptions = arrayOf("Public", "Private", "Invite")
        val statusLayout = binding.menuStatusBox
        val statusText = binding.menuStatus
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            statusOptions
        )
        statusText.setAdapter(adapter)
        statusText.setText("Public", false)

        statusText.setOnClickListener() {
            statusText.hideKeyboard()
        }
        statusText.doOnTextChanged() { text, start, before, count ->
            statusText.hideKeyboard()
            if (text.toString() == "Public") {
                binding.tfRoomPasswordBox.visibility = View.GONE
                binding.linearLayoutAddStudent.visibility = View.GONE

            } else if (text.toString() == "Private") {
                binding.tfRoomPasswordBox.visibility = View.VISIBLE
                binding.linearLayoutAddStudent.visibility = View.GONE

            } else if (text.toString() == "Invite") {
                binding.tfRoomPasswordBox.visibility = View.GONE
                binding.linearLayoutAddStudent.visibility = View.VISIBLE

            } else {
                statusText.hideKeyboard()
            }
        }

        binding.btnNewRoomSubmit.setOnClickListener() {
            submit(binding)
        }

        binding.btnCreateRoomCompleteHome.setOnClickListener() {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        createRoomViewModel.createRoomModelUiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    binding.progressLayout.visibility = View.VISIBLE
                }
                UiStateStatus.success -> {
                    binding.progressLayout.visibility = View.GONE
                    binding.completeLayout.visibility = View.VISIBLE
                    var description = uiState.data!!.data
                    binding.createRoomCompleteDescription.text = "Room Name: ${description!!.name}\nRoom Code: ${description!!.room_code}\nRoom Password: ${description!!.password}"
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
                UiStateStatus.error -> {
                    binding.progressLayout.visibility = View.GONE
                    Snackbar.make(binding.root, uiState.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun submit(binding: FragementCreateRoomBinding){
        val roomName = binding.tfRoomName.text.toString()
        val roomStatus = binding.menuStatus.text.toString()
        val roomPassword = binding.tfRoomPassword.text.toString()

        if (roomName.isEmpty()) {
            binding.tfRoomName.error = "Room name is required"
            return
        }else{
            binding.tfRoomName.error = null
        }
        when (roomStatus) {
            "Public" -> {
                createRoomViewModel.createRoom(requireContext(), roomName, roomStatus, null)
            }
            "Private" -> {
                if (roomPassword.isEmpty()) {
                    binding.tfRoomPassword.error = "Password is required"
                    return
                }else{
                    binding.tfRoomPassword.error = null
                }
                createRoomViewModel.createRoom(requireContext(), roomName, roomStatus, roomPassword)
            }
            "Invite" -> {
                Snackbar.make(binding.root, "Invited", Snackbar.LENGTH_SHORT).show()
            }

    }


    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}