package com.example.project2.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface MockableConsumeApi {
    @GET("categories")
    fun getBring(): Call<List<Category>>

}