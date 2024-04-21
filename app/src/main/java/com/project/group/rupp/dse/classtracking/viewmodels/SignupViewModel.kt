package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.PostSignUp
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class SignupViewModel: ViewModel(){
    private var _signupModelUiState = MutableLiveData<UiState<Boolean>>()
    val signupModelUiState get() = _signupModelUiState

    fun signUp(context: Context, username: String, email: String, password: String){
        _signupModelUiState.value = UiState<Boolean>(UiStateStatus.loading)

        var apiService = RetrofitInstance.create(context)

        var signup = PostSignUp(username, email, password)

        apiService.signUp(signup).enqueue(object : retrofit2.Callback<Boolean>{
            override fun onResponse(call: retrofit2.Call<Boolean>, response: retrofit2.Response<Boolean>) {
                if(response.isSuccessful){
                    _signupModelUiState.value = UiState<Boolean>(UiStateStatus.success, data = response.body())
                }else{
                    _signupModelUiState.value = UiState<Boolean>(UiStateStatus.error)
                }
            }
            override fun onFailure(call: retrofit2.Call<Boolean>, t: Throwable) {
                _signupModelUiState.value = UiState<Boolean>(UiStateStatus.error)
            }
        })
    }
}