package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetPostTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.PostTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call

class TeacherAttendanceViewModel:ViewModel() {
    private var _teacherAttendanceUiState = MutableLiveData<UiState<Response<List<GetTeacherAttendance>>>>()
    private var _teacherCheckAttendanceUiState = MutableLiveData<UiState<Response<GetPostTeacherAttendance>>>()
    val teacherAttendanceUiState: LiveData<UiState<Response<List<GetTeacherAttendance>>>> get() = _teacherAttendanceUiState
    val teacherCheckAttendanceUiState: LiveData<UiState<Response<GetPostTeacherAttendance>>> get() = _teacherCheckAttendanceUiState

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

    fun checkAttendance(context: Context, memberId:String, active:Boolean, classId:String, date:String){
        _teacherCheckAttendanceUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val postTeacherAttendance = PostTeacherAttendance(active, date, memberId, classId)

        apiService.postTeacherAttendance(postTeacherAttendance).enqueue(object : retrofit2.Callback<Response<GetPostTeacherAttendance>> {
            override fun onResponse(
                call: Call<Response<GetPostTeacherAttendance>>,
                response: retrofit2.Response<Response<GetPostTeacherAttendance>>
            ) {
                if (response.isSuccessful) {
                    _teacherCheckAttendanceUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _teacherCheckAttendanceUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response<GetPostTeacherAttendance>>, t: Throwable) {
                _teacherCheckAttendanceUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}