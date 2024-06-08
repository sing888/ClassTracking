package com.project.group.rupp.dse.classtracking.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.group.rupp.dse.classtracking.RetrofitInstance
import com.project.group.rupp.dse.classtracking.models.GetNews
import com.project.group.rupp.dse.classtracking.models.PostNews
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.UiState
import com.project.group.rupp.dse.classtracking.models.UiStateStatus

class NewsViewModel : ViewModel() {
    private var _newsModelUiState = MutableLiveData<UiState<Response<List<GetNews>>>>()

    val newsModelUiState: LiveData<UiState<Response<List<GetNews>>>> get() = _newsModelUiState

    fun getTeacherNews(context: Context, classroomId: String) {

        _newsModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getTeacherNews(classroomId).enqueue(object : retrofit2.Callback<Response<List<GetNews>>> {
            override fun onResponse(
                call: retrofit2.Call<Response<List<GetNews>>>,
                response: retrofit2.Response<Response<List<GetNews>>>
            ) {
                if (response.isSuccessful) {
                    _newsModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _newsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    val errorMsg = "Error: ${response.message()} Code: ${response.code()}"
                    Log.e("studentNewsViewModel", errorMsg)
                }
            }

            override fun onFailure(call: retrofit2.Call<Response<List<GetNews>>>, t: Throwable) {
                val errorMsg = "Failure: ${t.message}"
                Log.e("studentNewsViewModel", errorMsg, t)
                _newsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
    fun getStudentNews(context: Context, ClassroomId: String) {
        _newsModelUiState.value = UiState(UiStateStatus.loading)

        val apiService = RetrofitInstance.create(context)

        apiService.getStudentNews(ClassroomId).enqueue(object : retrofit2.Callback<Response<List<GetNews>>> {
            override fun onResponse(
                call: retrofit2.Call<Response<List<GetNews>>>,
                response: retrofit2.Response<Response<List<GetNews>>>
            ) {
                if (response.isSuccessful) {
                    _newsModelUiState.value = UiState(UiStateStatus.success, data = response.body())
                } else {
                    _newsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${response.message()}")
                    val errorMsg = "Error: ${response.message()} Code: ${response.code()}"
                    Log.e("studentNewsViewModel", errorMsg)
                }
            }

            override fun onFailure(call: retrofit2.Call<Response<List<GetNews>>>, t: Throwable) {
                val errorMsg = "Failure: ${t.message}"
                Log.e("studentNewsViewModel", errorMsg, t)
                _newsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }

    fun postNews(
        context: Context,
        newsTitle: String,
        newsDeadline: String,
        newsDescription: String,
        classroomId: String
    ) {
        _newsModelUiState.value = UiState(UiStateStatus.loading)

        if (newsTitle.isEmpty()) {
            _newsModelUiState.value = UiState(UiStateStatus.error, message = "Please enter your news title")
            return
        }
        if (newsDeadline.isEmpty()) {
            _newsModelUiState.value = UiState(UiStateStatus.error, message = "Please enter your news deadline")
            return
        }
        if (newsDescription.isEmpty()) {
            _newsModelUiState.value = UiState(UiStateStatus.error, message = "Please enter your news description")
            return
        }

        val postNews = PostNews(newsTitle, newsDeadline, newsDescription, classroomId)

        val apiService = RetrofitInstance.create(context)

        apiService.postNews(postNews).enqueue(object : retrofit2.Callback<Response<PostNews>> {
            override fun onResponse(
                call: retrofit2.Call<Response<PostNews>>,
                response: retrofit2.Response<Response<PostNews>>
            ) {
                if (response.isSuccessful) {
                    _newsModelUiState.value = UiState(UiStateStatus.success, message = "News posted successfully")

                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = "Error: ${response.message()} Code: ${response.code()} Body: $errorBody"
                    Log.e("NewsViewModelOnResponse", errorMsg)
                    _newsModelUiState.value = UiState(UiStateStatus.error, message = errorMsg)
                }
            }

            override fun onFailure(call: retrofit2.Call<Response<PostNews>>, t: Throwable) {
                val errorMsg = "Failure: ${t.message}"
                Log.e("NewsViewModel", errorMsg, t)
                _newsModelUiState.value = UiState(UiStateStatus.error, message = "Error: ${t.message}")
            }
        })
    }
}