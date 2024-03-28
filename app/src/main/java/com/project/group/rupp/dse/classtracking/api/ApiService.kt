package com.project.group.rupp.dse.classtracking.api

import retrofit2.http.GET
import retrofit2.Call
import com.project.group.rupp.dse.classtracking.models.Profile

public interface ApiService {
    @GET("profile")
    fun getProfile(): Call<Profile>

}