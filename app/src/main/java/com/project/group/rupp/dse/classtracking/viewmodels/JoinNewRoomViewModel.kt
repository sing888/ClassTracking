package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.PostJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class JoinNewRoomViewModel: ViewModel(){
    private val _joinNewRoomModelUiState = MutableLiveData<UiState<Response<GetJoinNewRoom>>>()
    val joinNewRoomModelUiState: LiveData<UiState<Response<GetJoinNewRoom>>> get() = _joinNewRoomModelUiState

    fun joinNewRoom(context: Context, roomCode: String, name: String?, password: String?){
        _joinNewRoomModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        var joinNewRoom = PostJoinNewRoom(roomCode, name, password)

        apiService.joinRoom(joinNewRoom).enqueue(object : retrofit2.Callback<Response<GetJoinNewRoom>>{
            override fun onResponse(call: retrofit2.Call<Response<GetJoinNewRoom>>, response: retrofit2.Response<Response<GetJoinNewRoom>>) {
                if(response.isSuccessful){
                    _joinNewRoomModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _joinNewRoomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<GetJoinNewRoom>>, t: Throwable) {
                _joinNewRoomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}