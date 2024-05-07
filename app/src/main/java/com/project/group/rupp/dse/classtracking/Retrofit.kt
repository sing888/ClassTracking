package com.project.group.rupp.dse.classtracking

import PreferenceUtils
import android.content.Context
import com.project.group.rupp.dse.classtracking.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {
    private const val BASE_URL = "https://classtracking-back.onrender.com/"

    fun create(context: Context): ApiService {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer " + PreferenceUtils.getToken(context))
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

}