package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreDetail
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class StudentScoreDetailViewModel: ViewModel() {
    private var _studentScoreDetailUiState = MutableLiveData<UiState<Response<GetStudentScoreDetail>>>()
    val studentScoreDetailUiState: LiveData<UiState<Response<GetStudentScoreDetail>>> get() = _studentScoreDetailUiState

    fun getStudentScoreDetail(context: Context, classId: String, memberId: String){
        _studentScoreDetailUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentScoreDetail(classroom_id = classId, member_id = memberId)
            .enqueue(object : retrofit2.Callback<Response<GetStudentScoreDetail>> {
                override fun onResponse(call: retrofit2.Call<Response<GetStudentScoreDetail>>, response: retrofit2.Response<Response<GetStudentScoreDetail>>) {
                    if (response.isSuccessful) {
                        _studentScoreDetailUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _studentScoreDetailUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: retrofit2.Call<Response<GetStudentScoreDetail>>, t: Throwable) {
                    _studentScoreDetailUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }

            })
    }

}