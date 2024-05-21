package com.project.group.rupp.dse.classtracking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Dictionary

class RoomMainViewModel: ViewModel() {
    private val _roomId = MutableLiveData<String>()
    val roomId: LiveData<String> get() = _roomId

    fun setRoomId(value: String) {
        _roomId.value = value
    }


}