package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAttendanceDetailAll
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class TeacherAttendanceDetailViewModel: ViewModel() {
    private var _attendanceDetailUiState = MutableLiveData<UiState<Response<List<GetAttendanceDetailAll>>>>()
    val attendanceDetailUiState: LiveData<UiState<Response<List<GetAttendanceDetailAll>>>> get() = _attendanceDetailUiState

    fun getAttendanceDetail(context: Context, classId: String) {
        _attendanceDetailUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getTeacherAttendanceDetail(classId)
            .enqueue(object : retrofit2.Callback<Response<List<GetAttendanceDetailAll>>> {
                override fun onResponse(
                    call: retrofit2.Call<Response<List<GetAttendanceDetailAll>>>,
                    response: retrofit2.Response<Response<List<GetAttendanceDetailAll>>>
                ) {
                    if (response.isSuccessful) {
                        _attendanceDetailUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _attendanceDetailUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<List<GetAttendanceDetailAll>>>,
                    t: Throwable
                ) {
                    _attendanceDetailUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }
}