package com.project.group.rupp.dse.classtracking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel(){
    private var _account_id = MutableLiveData<String>()
    val account_id: LiveData<String> get() = _account_id

    fun setAccountId(account_id: String){
        _account_id.value = account_id
    }
}