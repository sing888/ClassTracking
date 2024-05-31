package com.project.group.rupp.dse.classtracking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Dictionary

class RoomMainViewModel: ViewModel() {
    private val _roomId = MutableLiveData<String>()
    private val _account_id = MutableLiveData<String>()
    val roomId: LiveData<String> get() = _roomId
    val account_id: LiveData<String> get() = _account_id


    fun setRoomId(value: String) {
        _roomId.value = value
    }
    fun setAccountId(value: String) {
        _account_id.value = value
    }



}