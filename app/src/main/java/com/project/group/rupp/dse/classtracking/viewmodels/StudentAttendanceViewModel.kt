package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendance
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendanceDetail
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call
import java.util.Objects

class StudentAttendanceViewModel: ViewModel() {
    private var _attendancemodelUiState = MutableLiveData<UiState<Response<GetStudentAttendance>>>()
    private var _attendanceDetailUiState = MutableLiveData<UiState<Response<List<GetStudentAttendanceDetail>>>>()
    val attendancemodelUiState: LiveData<UiState<Response<GetStudentAttendance>>> get() = _attendancemodelUiState
    val attendanceDetailUiState: LiveData<UiState<Response<List<GetStudentAttendanceDetail>>>> get() = _attendanceDetailUiState

    fun getStudentAttendance(context: Context, classroom_id: String) {
        _attendancemodelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentAttendance(classroom_id).enqueue(object : retrofit2.Callback<Response<GetStudentAttendance>>{
            override fun onResponse(call: Call<Response<GetStudentAttendance>>, response: retrofit2.Response<Response<GetStudentAttendance>>) {
                if(response.isSuccessful){
                    _attendancemodelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _attendancemodelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response<GetStudentAttendance>>, t: Throwable) {
                _attendancemodelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun getStudentAttendanceDetail(context: Context, classroom_id: String) {
        _attendanceDetailUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentAttendanceDetail(classroom_id).enqueue(object : retrofit2.Callback<Response<List<GetStudentAttendanceDetail>>>{
            override fun onResponse(call: Call<Response<List<GetStudentAttendanceDetail>>>, response: retrofit2.Response<Response<List<GetStudentAttendanceDetail>>>){
                if(response.isSuccessful){
                    _attendanceDetailUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _attendanceDetailUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response<List<GetStudentAttendanceDetail>>>, t: Throwable) {
                _attendanceDetailUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}