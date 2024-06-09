package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetMakeAchieve
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.PostChangeClassroom
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call

class RoomViewModel: ViewModel() {
    private var _roomModelUiState = MutableLiveData<UiState<Response<List<GetRoom>>>>()
    private var _achieveModelUiState = MutableLiveData<UiState<Response<GetMakeAchieve>>>()
    private var _roleModelUiState = MutableLiveData<UiState<Response<String>>>()
    private var _searchModelUiState = MutableLiveData<UiState<Response<List<GetRoom>>>>()
    private var _changeClassroomUiState = MutableLiveData<UiState<Response<String>>>()
    val roomModelUiState: LiveData<UiState<Response<List<GetRoom>>>> get() = _roomModelUiState
    val achieveModelUiState: LiveData<UiState<Response<GetMakeAchieve>>> get() = _achieveModelUiState
    val roleModelUiState: LiveData<UiState<Response<String>>> get() = _roleModelUiState
    val searchModelUiState: LiveData<UiState<Response<List<GetRoom>>>> get() = _searchModelUiState
    val changeClassroomUiState: LiveData<UiState<Response<String>>> get() = _changeClassroomUiState

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

    fun getMakeAchieve(context: Context, classroom_id: String){
        _achieveModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.makeAchieve(classroom_id).enqueue(object : retrofit2.Callback<Response<GetMakeAchieve>>{
            override fun onResponse(call: retrofit2.Call<Response<GetMakeAchieve>>, response: retrofit2.Response<Response<GetMakeAchieve>>) {
                if(response.isSuccessful){
                    _achieveModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _achieveModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Response<GetMakeAchieve>>, t: Throwable) {
                _achieveModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun getRole(context: Context, classId: String) {
        _roleModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getRole(classId).enqueue(object : retrofit2.Callback<Response<String>> {
            override fun onResponse(call: retrofit2.Call<Response<String>>,
                                    response: retrofit2.Response<Response<String>>) {
                if (response.isSuccessful) {
                    _roleModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _roleModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<String>>, t: Throwable) {
                _roleModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }

        })
    }

    fun getSearch(context: Context, search: String){
        _searchModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.searchRoom(search).enqueue(object : retrofit2.Callback<Response<List<GetRoom>>>{
            override fun onResponse(
                call: Call<Response<List<GetRoom>>>,
                response: retrofit2.Response<Response<List<GetRoom>>>
            ) {
                if(response.isSuccessful){
                    _searchModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _searchModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response<List<GetRoom>>>, t: Throwable) {
                _searchModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun changeClassroom(context: Context, classroomId: String , className: String, status: String, password: String,){
        _changeClassroomUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val data = PostChangeClassroom(className, status, password)

        apiService.updateRoom(classroomId, data).enqueue(object : retrofit2.Callback<Response<String>>{
            override fun onResponse(call: Call<Response<String>>, response: retrofit2.Response<Response<String>>) {
                if(response.isSuccessful){
                    _changeClassroomUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _changeClassroomUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                _changeClassroomUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}