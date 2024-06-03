package com.example.project1.retrofit

import com.example.project2.retrofit.Flower
import retrofit2.Call
import retrofit2.http.GET

interface ConsumeApi {
    @GET("json/320/240/flower")
    fun getBring(): Call<Flower>

}