package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class SplashViewModel: ViewModel() {
    private var _splashModelUiState = MutableLiveData<UiState<Boolean>>()
    val splashModelUiState get() = _splashModelUiState

    fun checkToken(context: Context){
        _splashModelUiState.value = UiState(UiStateStatus.loading)

        var apiService = RetrofitInstance.create(context)


        apiService.checkToken().enqueue(object : retrofit2.Callback<Boolean>{
            override fun onResponse(call: retrofit2.Call<Boolean>, response: retrofit2.Response<Boolean>) {
                if(response.isSuccessful){
                    _splashModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _splashModelUiState.value = UiState(UiStateStatus.error)
                }
            }
            override fun onFailure(call: retrofit2.Call<Boolean>, t: Throwable) {
                _splashModelUiState.value = UiState(UiStateStatus.error)
            }
        })
    }
}