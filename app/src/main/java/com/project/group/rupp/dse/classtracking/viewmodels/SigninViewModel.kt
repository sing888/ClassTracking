package com.project.group.rupp.dse.classtracking.viewmodels
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetSignIn
import com.project.group.rupp.dse.classtracking.models.PostSignIn
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus



class SigninViewModel: ViewModel() {
    private var _signinModelUiState = MutableLiveData<UiState<GetSignIn>>()
    val signinModelUiState: LiveData<UiState<GetSignIn>> get() = _signinModelUiState

    fun signIn(context: Context , email: String, password: String){
        _signinModelUiState.value = UiState(UiStateStatus.loading)

        if (email.isEmpty()){
            _signinModelUiState.value = UiState(UiStateStatus.error, message = "Please enter your email")
            return
        }

        if (password.isEmpty()){
            _signinModelUiState.value = UiState(UiStateStatus.error, message = "Please enter your password")
            return
        }

        val signIn = PostSignIn(email, password)

        var apiService = RetrofitInstance.create(context)

        apiService.signIn(signIn).enqueue(object : retrofit2.Callback<GetSignIn>{
            override fun onResponse(call: retrofit2.Call<GetSignIn>, response: retrofit2.Response<GetSignIn>) {
                if(response.isSuccessful){
                    _signinModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                }else{
                    _signinModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<GetSignIn>, t: Throwable) {
                _signinModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })

    }

}