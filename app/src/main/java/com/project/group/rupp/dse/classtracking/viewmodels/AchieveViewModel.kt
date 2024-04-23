package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class AchieveViewModel: ViewModel() {
    private var _achieveModelUiState = MutableLiveData<UiState<Response<List<GetAchieve>>>>()
    val achieveModelUiState: LiveData<UiState<Response<List<GetAchieve>>>> get() = _achieveModelUiState

    fun getRoom(context: Context) {
        _achieveModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getAchieveclass().enqueue(object : retrofit2.Callback<Response<List<GetAchieve>>> {
            override fun onResponse(call: retrofit2.Call<Response<List<GetAchieve>>>,
                                    response: retrofit2.Response<Response<List<GetAchieve>>>) {
                if (response.isSuccessful) {
                    _achieveModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _achieveModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<List<GetAchieve>>>, t: Throwable) {
                _achieveModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }

        })
    }

}