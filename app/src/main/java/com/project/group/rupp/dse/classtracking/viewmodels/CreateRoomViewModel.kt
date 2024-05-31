package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetCreateRoom
import com.project.group.rupp.dse.classtracking.models.PostCreateRoom
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class CreateRoomViewModel: ViewModel() {
    private var _createRoomModelUiState = MutableLiveData<UiState<Response<GetCreateRoom>>>()
    val createRoomModelUiState: LiveData<UiState<Response<GetCreateRoom>>> get() = _createRoomModelUiState

    fun createRoom(context: Context, name: String, status: String, password: String?){
        _createRoomModelUiState.value = UiState(UiStateStatus.loading)

        var createRoom = PostCreateRoom(name, status, password)

        var apiService = RetrofitInstance.create(context)

        apiService.createRoom(createRoom).enqueue(object : retrofit2.Callback<Response<GetCreateRoom>>{
            override fun onResponse(call: retrofit2.Call<Response<GetCreateRoom>>, response: retrofit2.Response<Response<GetCreateRoom>>) {
                if(response.isSuccessful){
                    _createRoomModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _createRoomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<GetCreateRoom>>, t: Throwable) {
                _createRoomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}