package com.example.project2.retrofit

import com.example.project1.retrofit.ConsumeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.project2.utils.Constants.LOREM_FLICKR_URL

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(LOREM_FLICKR_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val consumeApi= retrofit.create(ConsumeApi::class.java)

}