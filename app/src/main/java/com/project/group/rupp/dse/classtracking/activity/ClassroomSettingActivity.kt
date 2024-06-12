package com.project.group.rupp.dse.classtracking.activity

import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.databinding.ActivityClassroomSettingBinding
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import com.project.group.rupp.dse.classtracking.viewmodels.RoomViewModel

class ClassroomSettingActivity: AppCompatActivity() {
    private var _binding: ActivityClassroomSettingBinding? = null
    private val binding get() = _binding!!
    private val roomViewModel: RoomViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityClassroomSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val room_id = intent.getStringExtra("room_id")
        val room_name = intent.getStringExtra("room_name")
        val room_type = intent.getStringExtra("room_type")

        var data : GetRoom? = null

        binding.roomBack.text = "<"
        binding.roomBack.setOnClickListener {
            finish()
        }
        binding.roomName.text = room_name

        if (room_type == "teacher") {
            roomViewModel.getTeacherRooms(this)
        } else {
            roomViewModel.getStudentRooms(this)
        }

        roomViewModel.roomModelUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {

                }
                UiStateStatus.success -> {
                    for (room in uiState.data!!.data!!) {
                        if (room.classroom_id == room_id) {
                            data = room
                            break
                        }
                    }
                    if (data != null) {
                        binding.ipRoomName.setText(data!!.name)
                        binding.ipPassword.setText(data?.password)
                        binding.menuStatus.setText(data!!.status)
                    }
                }
                UiStateStatus.error -> {
                    // show error
                }
            }
        })

        // dropdown status
        val statusOptions = arrayOf("Public", "Private") //, "Invite")
        val statusLayout = binding.menuStatusBox
        val statusText = binding.menuStatus
        val adapter = ArrayAdapter(
            this,
            R.layout.simple_dropdown_item_1line,
            statusOptions
        )
        statusText.setAdapter(adapter)
        statusText.setText("Public", false)

        statusText.setOnClickListener() {
//            statusText.hideKeyboard()
        }


        binding.btnSave.setOnClickListener() {
            roomViewModel.changeClassroom(this, data!!.classroom_id, binding.ipRoomName.text.toString(),  binding.menuStatus.text.toString(), binding.ipPassword.text.toString())
        }

        roomViewModel.changeClassroomUiState.observe(this, Observer { uiState ->
            when (uiState.status) {
                UiStateStatus.loading -> {
                    Snackbar.make(binding.root, "Changing...", Snackbar.LENGTH_SHORT).show()
                }
                UiStateStatus.success -> {
                    Snackbar.make(binding.root, "Change success", Snackbar.LENGTH_SHORT).show()
                }
                UiStateStatus.error -> {
                    Snackbar.make(binding.root, uiState.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}