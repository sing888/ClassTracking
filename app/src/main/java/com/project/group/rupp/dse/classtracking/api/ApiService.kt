package com.project.group.rupp.dse.classtracking.api

import com.project.group.rupp.dse.classtracking.models.PostCreateRoom
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.models.GetCreateRoom
import com.project.group.rupp.dse.classtracking.models.GetJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.PostJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.PostSignIn
import com.project.group.rupp.dse.classtracking.models.PostSignUp
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

    // Add a new POST request to the API
    @POST("auth/sign_in")
    fun signIn(@Body signIn: PostSignIn): Call<Response<SignInData>>

    @POST("auth/sign_up")
    fun signUp(@Body signUp: PostSignUp): Call<Boolean>

    @GET("account/info")
    fun getProfile(): Call<Response<Profile>>

    @GET("classroom/achieve/get")
    fun getAchieveclass(): Call<Response<List<GetAchieve>>>

    @GET("teacher/classroom")
    fun getTeacherRoom(): Call<Response<List<GetRoom>>>

    @GET("student/classroom")
    fun getStudentRoom(): Call<Response<List<GetRoom>>>

    @POST("classroom/create")
    fun createRoom(@Body postCreateRoom: PostCreateRoom): Call<Response<GetCreateRoom>>

    @POST("classroom/join")
    fun joinRoom(@Body postJoinRoom: PostJoinNewRoom): Call<Response<GetJoinNewRoom>>



}