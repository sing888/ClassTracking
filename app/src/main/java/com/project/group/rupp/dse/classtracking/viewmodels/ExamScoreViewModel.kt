package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAddExamScore
import com.project.group.rupp.dse.classtracking.models.GetExamScoreDetail
import com.project.group.rupp.dse.classtracking.models.PostAddExamScore
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class ExamScoreViewModel:ViewModel() {
    private var _examScoreDetailUiState = MutableLiveData<UiState<Response<List<GetExamScoreDetail>>>>()
    private var _addExamScoreUiState = MutableLiveData<UiState<Response<GetAddExamScore>>>()
    val examScoreDetailUiState: LiveData<UiState<Response<List<GetExamScoreDetail>>>> get() = _examScoreDetailUiState
    val addExamScoreUiState: LiveData<UiState<Response<GetAddExamScore>>> get() = _addExamScoreUiState
    fun getExamScoreDetail(context: Context, examId: String) {
        _examScoreDetailUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getExamScore(examId)
            .enqueue(object : retrofit2.Callback<Response<List<GetExamScoreDetail>>> {
                override fun onResponse(
                    call: retrofit2.Call<Response<List<GetExamScoreDetail>>>,
                    response: retrofit2.Response<Response<List<GetExamScoreDetail>>>
                ) {
                    if (response.isSuccessful) {
                        _examScoreDetailUiState.value =
                            UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _examScoreDetailUiState.value =
                            UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<List<GetExamScoreDetail>>>,
                    t: Throwable
                ) {
                    _examScoreDetailUiState.value =
                        UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

    fun addExamScore(context: Context, score: String, examId: String, memberId: String) {
        _addExamScoreUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val data = PostAddExamScore(score, examId, memberId)

        apiService.addExamScore(data)
            .enqueue(object : retrofit2.Callback<Response<GetAddExamScore>> {
                override fun onResponse(
                    call: retrofit2.Call<Response<GetAddExamScore>>,
                    response: retrofit2.Response<Response<GetAddExamScore>>
                ) {
                    if (response.isSuccessful) {
                        _addExamScoreUiState.value =
                            UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _addExamScoreUiState.value =
                            UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<GetAddExamScore>>,
                    t: Throwable
                ) {
                    _addExamScoreUiState.value =
                        UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

}