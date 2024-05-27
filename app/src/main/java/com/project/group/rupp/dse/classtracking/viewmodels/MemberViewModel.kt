package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetMember
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class MemberViewModel:ViewModel() {
    private var _memberUiState = MutableLiveData<UiState<Response<List<GetMember>>>>()
    val memberUiState: LiveData<UiState<Response<List<GetMember>>>> get() = _memberUiState

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

}