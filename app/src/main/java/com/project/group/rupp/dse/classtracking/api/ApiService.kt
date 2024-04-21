package com.project.group.rupp.dse.classtracking.api

import com.project.group.rupp.dse.classtracking.models.PostSignIn
import retrofit2.http.GET
import retrofit2.Call
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.SignInData
import retrofit2.http.Body
import retrofit2.http.POST

public interface ApiService {
    @GET("auth/sign_in/token")
    fun checkToken(): Call<Boolean>

    @GET("account/info")
    fun getProfile(): Call<Response<Profile>>

    // Add a new POST request to the API
    @POST("auth/sign_in")
    fun signIn(@Body signIn: PostSignIn): Call<Response<SignInData>>



}