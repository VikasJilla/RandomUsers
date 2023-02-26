package com.example.randomusers.network

import com.example.randomusers.network.interceptors.HTTPLogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL = " https://randomuser.me/api/"

    private val okHttpClient:OkHttpClient = OkHttpClient().newBuilder().apply {
        addInterceptor(HTTPLogInterceptor())
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getRetrofit():Retrofit {
        return retrofit
    }

}