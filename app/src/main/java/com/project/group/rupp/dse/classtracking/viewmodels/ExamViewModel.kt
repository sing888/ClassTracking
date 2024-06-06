package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.compose.ui.window.Popup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAddExam
import com.project.group.rupp.dse.classtracking.models.GetExam
import com.project.group.rupp.dse.classtracking.models.GetExamScoreDetail
import com.project.group.rupp.dse.classtracking.models.PostAddExam
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class ExamViewModel: ViewModel(){
    private var _examUiState = MutableLiveData<UiState<Response<List<GetExam>>>>()
    private var _examAddUiState = MutableLiveData<UiState<Response<GetAddExam>>>()
    private var _examDeleteUiState = MutableLiveData<UiState<Response<String>>>()

    val examUiState: LiveData<UiState<Response<List<GetExam>>>> get() = _examUiState
    val examAddUiState: LiveData<UiState<Response<GetAddExam>>> get() = _examAddUiState
    val examDeleteUiState: LiveData<UiState<Response<String>>> get() = _examDeleteUiState


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

    fun addExam(context: Context, name: String, maxScore: String, subjectId: String){
        _examAddUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val data = PostAddExam(name, maxScore, subjectId)

        apiService.addExam(data)
            .enqueue(object : retrofit2.Callback<Response<GetAddExam>>{
                override fun onResponse(
                    call: retrofit2.Call<Response<GetAddExam>>,
                    response: retrofit2.Response<Response<GetAddExam>>
                ) {
                    if(response.isSuccessful){
                        _examAddUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _examAddUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<GetAddExam>>,
                    t: Throwable
                ) {
                    _examAddUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

    fun deleteExam(context: Context, examId: String){
        _examDeleteUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.deleteExam(examId)
            .enqueue(object : retrofit2.Callback<Response<String>>{
                override fun onResponse(
                    call: retrofit2.Call<Response<String>>,
                    response: retrofit2.Response<Response<String>>
                ) {
                    if(response.isSuccessful){
                        _examDeleteUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _examDeleteUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<String>>,
                    t: Throwable
                ) {
                    _examDeleteUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }
}
