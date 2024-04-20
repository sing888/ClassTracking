package com.project.group.rupp.dse.classtracking.api

import com.project.group.rupp.dse.classtracking.models.GetSignIn
import com.project.group.rupp.dse.classtracking.models.PostSignIn
import retrofit2.http.GET
import retrofit2.Call
import com.project.group.rupp.dse.classtracking.models.Profile
import retrofit2.http.Body
import retrofit2.http.POST

public interface ApiService {
    @GET("profile")
    fun getProfile(): Call<Profile>

    // Add a new POST request to the API
    @POST("auth/sign_in")
    fun signIn(@Body signIn: PostSignIn): Call<GetSignIn>

}