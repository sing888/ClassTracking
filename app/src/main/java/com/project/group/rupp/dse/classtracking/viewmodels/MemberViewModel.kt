package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAddMember
import com.project.group.rupp.dse.classtracking.models.GetMember
import com.project.group.rupp.dse.classtracking.models.PostMember
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class MemberViewModel:ViewModel() {
    private var _memberUiState = MutableLiveData<UiState<Response<List<GetMember>>>>()
    private var _memberAddUiState = MutableLiveData<UiState<Response<GetAddMember>>>()
    private var _memberDeleteUiState = MutableLiveData<UiState<Response<String>>>()
    val memberUiState: LiveData<UiState<Response<List<GetMember>>>> get() = _memberUiState
    val memberAddUiState: LiveData<UiState<Response<GetAddMember>>> get() = _memberAddUiState
    val memberDeleteUiState: LiveData<UiState<Response<String>>> get() = _memberDeleteUiState

    fun getMember(context:Context, classId:String){
        _memberUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getMember(classId).enqueue(object : retrofit2.Callback<Response<List<GetMember>>>{
            override fun onResponse(call: retrofit2.Call<Response<List<GetMember>>>, response: retrofit2.Response<Response<List<GetMember>>>) {
                if (response.isSuccessful){
                    _memberUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _memberUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<List<GetMember>>>, t: Throwable) {
                _memberUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun addMember(context: Context,name: String, classId: String ){
        _memberAddUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val member = PostMember(name, "student", classId)

        apiService.addMember(member).enqueue(object : retrofit2.Callback<Response<GetAddMember>>{
            override fun onResponse(call: retrofit2.Call<Response<GetAddMember>>, response: retrofit2.Response<Response<GetAddMember>>) {
                if (response.isSuccessful){
                    _memberAddUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _memberAddUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<GetAddMember>>, t: Throwable) {
                _memberAddUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun deleteMember(context: Context, memberId: String){
        _memberDeleteUiState.value = UiState(UiStateStatus.loading)
        val apiService = RetrofitInstance.create(context)

        apiService.deleteMember(memberId).enqueue(object : retrofit2.Callback<Response<String>>{
            override fun onResponse(call: retrofit2.Call<Response<String>>, response: retrofit2.Response<Response<String>>) {
                if (response.isSuccessful){
                    _memberDeleteUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _memberDeleteUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<String>>, t: Throwable) {
                _memberDeleteUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

}