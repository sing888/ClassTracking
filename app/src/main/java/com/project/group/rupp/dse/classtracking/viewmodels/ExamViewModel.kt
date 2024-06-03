package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetExam
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class ExamViewModel: ViewModel(){
    private var _examUiState = MutableLiveData<UiState<Response<List<GetExam>>>>()
    val examUiState: LiveData<UiState<Response<List<GetExam>>>> get() = _examUiState

    fun getExam(context: Context, subjectId: String){
        _examUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getExam(subjectId)
            .enqueue(object : retrofit2.Callback<Response<List<GetExam>>>{
                override fun onResponse(
                    call: retrofit2.Call<Response<List<GetExam>>>,
                    response: retrofit2.Response<Response<List<GetExam>>>
                ) {
                    if(response.isSuccessful){
                        _examUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _examUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<List<GetExam>>>,
                    t: Throwable
                ) {
                    _examUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }
}