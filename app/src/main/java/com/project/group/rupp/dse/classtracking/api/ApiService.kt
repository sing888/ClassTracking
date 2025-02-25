package com.project.group.rupp.dse.classtracking.api

import android.net.Uri
import android.widget.ImageView
import com.project.group.rupp.dse.classtracking.models.PostCreateRoom
import com.project.group.rupp.dse.classtracking.models.GetAchieve
import com.project.group.rupp.dse.classtracking.models.GetAddExam
import com.project.group.rupp.dse.classtracking.models.GetAddExamScore
import com.project.group.rupp.dse.classtracking.models.GetAddMember
import com.project.group.rupp.dse.classtracking.models.GetAddSubject
import com.project.group.rupp.dse.classtracking.models.GetAttendanceDetailAll
import com.project.group.rupp.dse.classtracking.models.GetNews
import com.project.group.rupp.dse.classtracking.models.GetCreateRoom
import com.project.group.rupp.dse.classtracking.models.GetExam
import com.project.group.rupp.dse.classtracking.models.GetExamScoreDetail
import com.project.group.rupp.dse.classtracking.models.GetJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.GetMakeAchieve
import com.project.group.rupp.dse.classtracking.models.GetMember
import com.project.group.rupp.dse.classtracking.models.GetMemberID
import com.project.group.rupp.dse.classtracking.models.GetPostTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.GetRoom
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendance
import com.project.group.rupp.dse.classtracking.models.GetStudentAttendanceDetail
import com.project.group.rupp.dse.classtracking.models.GetStudentScore
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreDetail
import com.project.group.rupp.dse.classtracking.models.GetStudentScoreList
import com.project.group.rupp.dse.classtracking.models.GetTeacherAttendance
import com.project.group.rupp.dse.classtracking.models.PostJoinNewRoom
import com.project.group.rupp.dse.classtracking.models.PostMember
import com.project.group.rupp.dse.classtracking.models.GetTeacherSubject
import com.project.group.rupp.dse.classtracking.models.PostAccountDetail
import com.project.group.rupp.dse.classtracking.models.PostAddExam
import com.project.group.rupp.dse.classtracking.models.PostAddExamScore
import com.project.group.rupp.dse.classtracking.models.PostAddSubject
import com.project.group.rupp.dse.classtracking.models.PostChangeClassroom
import com.project.group.rupp.dse.classtracking.models.PostNews
import com.project.group.rupp.dse.classtracking.models.PostSignIn
import com.project.group.rupp.dse.classtracking.models.PostSignUp
import com.project.group.rupp.dse.classtracking.models.PostTeacherAttendance
import retrofit2.http.GET
import retrofit2.Call
import com.project.group.rupp.dse.classtracking.models.Profile
import com.project.group.rupp.dse.classtracking.models.Response
import com.project.group.rupp.dse.classtracking.models.SignInData
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

public interface ApiService {
    @POST("auth/sign_in")
    fun signIn(@Body signIn: PostSignIn): Call<Response<SignInData>>

    @POST("auth/sign_up")
    fun signUp(@Body signUp: PostSignUp): Call<Boolean>

    @POST("teacher/news")
    fun postNews(@Body postNews: PostNews): Call<Response<PostNews>>

    @POST("classroom/create")
    fun createRoom(@Body postCreateRoom: PostCreateRoom): Call<Response<GetCreateRoom>>

    @POST("classroom/join")
    fun joinRoom(@Body postJoinRoom: PostJoinNewRoom): Call<Response<GetJoinNewRoom>>

    @POST("teacher/attendance")
    fun postTeacherAttendance(@Body postTeacherAttendance: PostTeacherAttendance): Call<Response<GetPostTeacherAttendance>>

    @POST("teacher/member")
    fun addMember(@Body postAddMember: PostMember): Call<Response<GetAddMember>>

    @POST("teacher/subject")
    fun addSubject(
        @Body postAddSubject: PostAddSubject
    ): Call<Response<GetAddSubject>>

    @POST("teacher/exam")
    fun addExam(
        @Body postAddExam: PostAddExam
    ): Call<Response<GetAddExam>>

    @POST("teacher/exam/score")
    fun addExamScore(
        @Body postAddExamScore: PostAddExamScore
    ): Call<Response<GetAddExamScore>>

    @GET("auth/sign_in/token")
    fun checkToken(): Call<Boolean>

    @GET("account/info")
    fun getProfile(): Call<Response<Profile>>

    @GET("classroom/achieve/get")
    fun getAchieveclass(): Call<Response<List<GetAchieve>>>

    @GET("classroom/role")
    fun getRole(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<String>>

    @GET("teacher/classroom")
    fun getTeacherRoom(): Call<Response<List<GetRoom>>>

    @GET("student/classroom")
    fun getStudentRoom(): Call<Response<List<GetRoom>>>

    @GET("teacher/news")
    fun getTeacherNews(@Query("classroom_id") classroom_id: String): Call<Response<List<GetNews>>>

    @GET("student/news")
    fun getStudentNews(@Query("classroom_id") classroom_id: String): Call<Response<List<GetNews>>>

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

    @GET("teacher/attendance/percentage")
    fun getTeacherAttendanceDetail(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<List<GetAttendanceDetailAll>>>

    @GET("teacher/subject")
    fun getTeacherSubject(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<List<GetTeacherSubject>>>

    @GET("teacher/exam")
    fun getExam(
        @Query("subject_id") subject_id: String
    ): Call<Response<List<GetExam>>>

    @GET("teacher/exam/score")
    fun getExamScore(
        @Query("exam_id") exam_id: String
    ): Call<Response<List<GetExamScoreDetail>>>

    @GET("classroom/search")
    fun searchRoom(
        @Query("search") search: String
    ): Call<Response<List<GetRoom>>>

    @GET("classroom/search/tip")
    fun searchRoomTip(
        @Query("search") search: String
    ): Call<Response<List<String>>>

    @PUT("classroom/achieve/make")
    fun makeAchieve(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<GetMakeAchieve>>

    @PUT("classroom/unachievable/make")
    fun makeUnAchieve(
        @Query("classroom_id") classroom_id: String
    ): Call<Response<GetMakeAchieve>>

    @PUT("account/change/account/detail")
    fun changeAccountDetail(
        @Body postAccountDetail: PostAccountDetail
    ): Call<Response<String>>

    @Headers(
        "accept: application/json",
        "Content-Type: multipart/form-data"
    )
    @Multipart
    @PUT("account/change/profile")
    fun changeProfile(
        @Part image: MultipartBody.Part,
    ): Call<Response<String>>

    @PUT("classroom/update")
    fun updateRoom(
        @Query("classroom_id") classroom_id: String,
        @Body postChangeClassroom: PostChangeClassroom
    ): Call<Response<String>>

    @DELETE("teacher/member")
    fun deleteMember(
        @Query("member_id") member_id: String
    ): Call<Response<String>>

    @DELETE("teacher/subject")
    fun deleteSubject(
        @Query("subject_id") subject_id: String
    ): Call<Response<String>>

    @DELETE("teacher/exam")
    fun deleteExam(
        @Query("exam_id") exam_id: String
    ): Call<Response<String>>

}