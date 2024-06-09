package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.PostAccountDetail
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import okhttp3.MultipartBody
import retrofit2.Call
import java.io.File

class ProfileViewModel: ViewModel(){

    private val _profileModelUiState = MutableLiveData<UiState<Response<Profile>>>()
    private val _updateProfileModelUiState = MutableLiveData<UiState<Response<String>>>()
    private val _changeProfileModelUiState = MutableLiveData<UiState<Response<String>>>()
    val profileModelUiState: LiveData<UiState<Response<Profile>>> get() = _profileModelUiState
    val updateProfileModelUiState: LiveData<UiState<Response<String>>> get() = _updateProfileModelUiState
    val changeProfileModelUiState: LiveData<UiState<Response<String>>> get() = _changeProfileModelUiState

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

    fun updateProfile(context: Context, username: String){
        _updateProfileModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        val data = PostAccountDetail(username)

        apiService.changeAccountDetail(data).enqueue(object : retrofit2.Callback<Response<String>>{
            override fun onResponse(call: Call<Response<String>>, response: retrofit2.Response<Response<String>>) {
                if (response.isSuccessful){
                    _updateProfileModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _updateProfileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                _updateProfileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun changeProfile(context: Context, imageView: MultipartBody.Part){
        _changeProfileModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.changeProfile(imageView).enqueue(object : retrofit2.Callback<Response<String>>{
            override fun onResponse(call: Call<Response<String>>, response: retrofit2.Response<Response<String>>) {
                if (response.isSuccessful){
                    _changeProfileModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _changeProfileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                _changeProfileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}