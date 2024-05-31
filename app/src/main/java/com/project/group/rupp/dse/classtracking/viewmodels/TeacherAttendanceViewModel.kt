package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call

class TeacherAttendanceViewModel:ViewModel() {
    private var _teacherAttendanceUiState = MutableLiveData<UiState<Response<List<GetTeacherAttendance>>>>()
    val teacherAttendanceUiState: LiveData<UiState<Response<List<GetTeacherAttendance>>>> get() = _teacherAttendanceUiState

    fun getTeacherAttendance(context: Context, classId:String, date:String){
        _teacherAttendanceUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getTeacherAttendance(classId, date)
            .enqueue(object : retrofit2.Callback<Response<List<GetTeacherAttendance>>> {
                override fun onResponse(
                    call: Call<Response<List<GetTeacherAttendance>>>,
                    response: retrofit2.Response<Response<List<GetTeacherAttendance>>>
                ) {
                    if (response.isSuccessful) {
                        _teacherAttendanceUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _teacherAttendanceUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: Call<Response<List<GetTeacherAttendance>>>,
                    t: Throwable
                ) {
                    _teacherAttendanceUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }

            })
    }
}