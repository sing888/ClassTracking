package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAddSubject
import com.project.group.rupp.dse.classtracking.models.GetTeacherSubject
import com.project.group.rupp.dse.classtracking.models.PostAddSubject
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class TeacherScoreViewModel: ViewModel(){
    private var _subjectUiState = MutableLiveData<UiState<Response<List<GetTeacherSubject>>>>()
    private var _addSubjectUiState = MutableLiveData<UiState<Response<GetAddSubject>>>()
    private var _deleteSubjectUiState = MutableLiveData<UiState<Response<String>>>()
    val subjectUiState: LiveData<UiState<Response<List<GetTeacherSubject>>>> get() = _subjectUiState
    val addSubjectUiState: LiveData<UiState<Response<GetAddSubject>>> get() = _addSubjectUiState
    val deleteSubjectUiState: LiveData<UiState<Response<String>>> get() = _deleteSubjectUiState

    fun getTeacherSubject(context: Context, classID: String){
        _subjectUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getTeacherSubject(classID)
            .enqueue(object : retrofit2.Callback<Response<List<GetTeacherSubject>>>{
                override fun onResponse(
                    call: retrofit2.Call<Response<List<GetTeacherSubject>>>,
                    response: retrofit2.Response<Response<List<GetTeacherSubject>>>
                ) {
                    if(response.isSuccessful){
                        _subjectUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _subjectUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<List<GetTeacherSubject>>>,
                    t: Throwable
                ) {
                    _subjectUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

    fun addSubject(context: Context, name: String, classroom_id: String){
        _addSubjectUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val data = PostAddSubject(name, classroom_id)

        apiService.addSubject(data)
            .enqueue(object : retrofit2.Callback<Response<GetAddSubject>>{
                override fun onResponse(
                    call: retrofit2.Call<Response<GetAddSubject>>,
                    response: retrofit2.Response<Response<GetAddSubject>>
                ) {
                    if(response.isSuccessful){
                        _addSubjectUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _addSubjectUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<GetAddSubject>>,
                    t: Throwable
                ) {
                    _addSubjectUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }

    fun deleteSubject(context: Context, subjectID: String){
        _deleteSubjectUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.deleteSubject(subjectID)
            .enqueue(object : retrofit2.Callback<Response<String>>{
                override fun onResponse(
                    call: retrofit2.Call<Response<String>>,
                    response: retrofit2.Response<Response<String>>
                ) {
                    if(response.isSuccessful){
                        _deleteSubjectUiState.value = UiState(UiStateStatus.success, data = response.body())
                    } else {
                        _deleteSubjectUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<Response<String>>,
                    t: Throwable
                ) {
                    _deleteSubjectUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
                }
            })
    }
}