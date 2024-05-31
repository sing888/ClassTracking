package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetTeacherNews
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call

class TeacherNewsViewModel: ViewModel() {
    private var _teacherNewsModelUiState = MutableLiveData<UiState<Response<List<GetTeacherNews>>>>()

    val teacherNewsModelUiState: LiveData<UiState<Response<List<GetTeacherNews>>>> get() = _teacherNewsModelUiState

    fun getTeacherNews(context: Context){
        _teacherNewsModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getTeacherNews().enqueue(object : retrofit2.Callback<Response<List<GetTeacherNews>>>{
            override fun onResponse(
                call: Call<Response<List<GetTeacherNews>>>,
                response: retrofit2.Response<Response<List<GetTeacherNews>>>
            ) {
                if (response.isSuccessful){
                    _teacherNewsModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _teacherNewsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response<List<GetTeacherNews>>>, t: Throwable) {
                _teacherNewsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }

        })
    }
}