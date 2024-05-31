package com.project.group.rupp.dse.classtracking.api

import com.project.group.rupp.dse.classtracking.models.PostCreateRoom
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.models.GetTeacherNews
import com.project.group.rupp.dse.classtracking.models.GetCreateRoom
import com.project.group.rupp.dse.classtracking.models.GetJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.GetMember
import com.project.group.rupp.dse.classtracking.models.GetMemberID
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendance
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendanceDetail
import com.project.group.rupp.dse.classtracking.models.GetStudentScore
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreDetail
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreList
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.PostJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.GetStudentNews
import com.project.group.rupp.dse.classtracking.models.PostSignIn
import com.project.group.rupp.dse.classtracking.models.PostSignUp
import retrofit2.http.GET
import retrofit2.Call
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.SignInData
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("teacher/news")
    fun getTeacherNews(): Call<Response<List<GetTeacherNews>>>

    @GET("student/news")
    fun getStudentNews(): Call<Response<List<GetStudentNews>>>

    @POST("classroom/create")
    fun createRoom(@Body postCreateRoom: PostCreateRoom): Call<Response<GetCreateRoom>>

    @POST("classroom/join")
    fun joinRoom(@Body postJoinRoom: PostJoinNewRoom): Call<Response<GetJoinNewRoom>>

    @GET("student/attendance/percentage")
    fun getStudentAttendance(@Query("classroom_id") classroom_id: String): Call<Response<GetStudentAttendance>>

    @GET("student/attendance")
    fun getStudentAttendanceDetail(@Query("classroom_id") classroom_id: String): Call<Response<List<GetStudentAttendanceDetail>>>

    @GET("student/score/account/member_id")
    fun getMemberId(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<GetMemberID>>

    @GET("student/score")
    fun getStudentScore(
        @Query("classroom_id") classroom_id: String,
        @Query("member_id") member_id: String
    ): Call<Response<GetStudentScore>>

    @GET("student/score/list")
    fun getStudentScoreList(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<List<GetStudentScoreList>>>

    @GET("student/score/percentage")
    fun getStudentScoreDetail(
        @Query("classroom_id") classroom_id: String,
        @Query("member_id") member_id: String
    ): Call<Response<GetStudentScoreDetail>>

    @GET("teacher/member")
    fun getMember(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<List<GetMember>>>

    @GET("teacher/attendance")
    fun getTeacherAttendance(
        @Query("classroom_id") classroom_id: String,
        @Query("date") date: String
    ): Call<Response<List<GetTeacherAttendance>>>


}