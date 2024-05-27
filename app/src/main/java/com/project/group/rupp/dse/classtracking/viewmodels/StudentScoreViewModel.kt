package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetMemberID
import com.project.group.rupp.dse.classtracking.models.GetStudentScore
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreList
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call

class StudentScoreViewModel: ViewModel() {
    private var _memberIdModelUiState = MutableLiveData<UiState<Response<GetMemberID>>>()
    private var _studentScoreModelUiState = MutableLiveData<UiState<Response<GetStudentScore>>>()
    private var _studentScoreListUiState = MutableLiveData<UiState<List<GetStudentScoreList>>>()
    val memberIdModelUiState: LiveData<UiState<Response<GetMemberID>>> get() = _memberIdModelUiState
    val studentScoreModelUiState: LiveData<UiState<Response<GetStudentScore>>> get() = _studentScoreModelUiState
    val studentScoreListUiState: LiveData<UiState<List<GetStudentScoreList>>> get() = _studentScoreListUiState

    fun getMemberId(context: Context, classId: String) {
        _memberIdModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getMemberId(classId.toString())
            .enqueue(object : retrofit2.Callback<Response<GetMemberID>> {
                override fun onResponse(
                    call: Call<Response<GetMemberID>>,
                    response: retrofit2.Response<Response<GetMemberID>>
                ) {
                    if (response.isSuccessful) {
                        _memberIdModelUiState.value =
                            UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _memberIdModelUiState.value =
                            UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Response<GetMemberID>>, t: Throwable) {
                    _memberIdModelUiState.value =
                        UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

    fun getStudentScore(context: Context, memberId: String, classId: String){
        _studentScoreModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentScore(classroom_id = classId, member_id = memberId)
            .enqueue(object : retrofit2.Callback<Response<GetStudentScore>> {
                override fun onResponse(call: Call<Response<GetStudentScore>>, response: retrofit2.Response<Response<GetStudentScore>>) {
                    if (response.isSuccessful) {
                        _studentScoreModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _studentScoreModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Response<GetStudentScore>>, t: Throwable) {
                    _studentScoreModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

    fun getStudentScoreList(context: Context, classId: String){
        _studentScoreListUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentScoreList(classroom_id = classId)
            .enqueue(object : retrofit2.Callback<Response<List<GetStudentScoreList>>> {
                override fun onResponse(call: Call<Response<List<GetStudentScoreList>>>, response: retrofit2.Response<Response<List<GetStudentScoreList>>>) {
                    if (response.isSuccessful) {
                        _studentScoreListUiState.value = UiState(UiStateStatus.success, data = response.body()?.data)
                    } else {
                        _studentScoreListUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Response<List<GetStudentScoreList>>>, t: Throwable) {
                    _studentScoreListUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }


}