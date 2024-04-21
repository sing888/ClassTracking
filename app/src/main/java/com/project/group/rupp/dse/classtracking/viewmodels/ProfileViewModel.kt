package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.api.ApiService
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProfileViewModel: ViewModel(){

    private val _profileModelUiState = MutableLiveData<UiState<Response<Profile>>>()
    val profileModelUiState: LiveData<UiState<Response<Profile>>> get() = _profileModelUiState

    fun getProfile(context: Context){
        _profileModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getProfile().enqueue(object : retrofit2.Callback<Response<Profile>>{
            override fun onResponse(call: Call<Response<Profile>>, response: retrofit2.Response<Response<Profile>>) {
                if (response.isSuccessful){
                    _profileModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _profileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Response<Profile>>, t: Throwable) {
                _profileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}