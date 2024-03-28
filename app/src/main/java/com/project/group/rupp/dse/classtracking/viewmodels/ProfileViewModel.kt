package com.project.group.rupp.dse.classtracking.viewmodels

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.api.ApiService
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProfileViewModel: ViewModel(){

    private val _profileModelUiState = MutableLiveData<UiState<Profile>>()
    val profileModelUiState: LiveData<UiState<Profile>> get() = _profileModelUiState

    fun getProfile(){
        _profileModelUiState.value = UiState(UiStateStatus.loading)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://alme1-j37m1twm.b4a.run/mad/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create<ApiService>()

        apiService.getProfile().enqueue(object : retrofit2.Callback<Profile>{
            override fun onResponse(call: retrofit2.Call<Profile>, response: retrofit2.Response<Profile>) {
                if(response.isSuccessful){
                    _profileModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _profileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Profile>, t: Throwable) {
                _profileModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }


}