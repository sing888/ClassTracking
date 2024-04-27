package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class RoomViewModel: ViewModel() {
    private var _roomModelUiState = MutableLiveData<UiState<Response<List<GetRoom>>>>()
    val roomModelUiState: LiveData<UiState<Response<List<GetRoom>>>> get() = _roomModelUiState

    fun getTeacherRooms(context: Context){
        _roomModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getTeacherRoom().enqueue(object : retrofit2.Callback<Response<List<GetRoom>>>{
            override fun onResponse(call: retrofit2.Call<Response<List<GetRoom>>>, response: retrofit2.Response<Response<List<GetRoom>>>) {
                if(response.isSuccessful){
                    _roomModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _roomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Response<List<GetRoom>>>, t: Throwable) {
                _roomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun getStudentRooms(context: Context){
        _roomModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentRoom().enqueue(object : retrofit2.Callback<Response<List<GetRoom>>>{
            override fun onResponse(call: retrofit2.Call<Response<List<GetRoom>>>, response: retrofit2.Response<Response<List<GetRoom>>>) {
                if(response.isSuccessful){
                    _roomModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _roomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Response<List<GetRoom>>>, t: Throwable) {
                _roomModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}