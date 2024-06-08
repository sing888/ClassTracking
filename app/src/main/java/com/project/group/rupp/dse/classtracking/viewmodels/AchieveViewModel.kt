package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.models.GetMakeAchieve
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class AchieveViewModel: ViewModel() {
    private var _achieveModelUiState = MutableLiveData<UiState<Response<List<GetAchieve>>>>()
    private var _roleModelUiState = MutableLiveData<UiState<Response<String>>>()
    private var _unachieveModelUiState = MutableLiveData<UiState<Response<GetMakeAchieve>>>()
    val achieveModelUiState: LiveData<UiState<Response<List<GetAchieve>>>> get() = _achieveModelUiState
    val roleModelUiState: LiveData<UiState<Response<String>>> get() = _roleModelUiState
    val unachieveModelUiState: LiveData<UiState<Response<GetMakeAchieve>>> get() = _unachieveModelUiState

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

    fun getRole(context: Context, classId: String) {
        _roleModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getRole(classId).enqueue(object : retrofit2.Callback<Response<String>> {
            override fun onResponse(call: retrofit2.Call<Response<String>>,
                                    response: retrofit2.Response<Response<String>>) {
                if (response.isSuccessful) {
                    _roleModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _roleModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<String>>, t: Throwable) {
                _roleModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }

        })
    }

    fun getUnachieve(context: Context, classId: String) {
        _unachieveModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.makeUnAchieve(classId).enqueue(object : retrofit2.Callback<Response<GetMakeAchieve>> {
            override fun onResponse(call: retrofit2.Call<Response<GetMakeAchieve>>,
                                    response: retrofit2.Response<Response<GetMakeAchieve>>) {
                if (response.isSuccessful) {
                    _unachieveModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _unachieveModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<Response<GetMakeAchieve>>, t: Throwable) {
                _unachieveModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }

        })
    }

}