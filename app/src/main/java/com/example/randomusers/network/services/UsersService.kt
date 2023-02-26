package com.example.randomusers.network.services

import com.example.randomusers.data.models.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET(".")
    suspend fun getUsers(@Query("page") pageNumber:Int,@Query("results") results:Int = 20,@Query("seed") seed:String = "random"):Response<Results>
}