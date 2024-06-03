package com.example.project2.retrofit

import com.example.project1.retrofit.FlowersConsumeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.project2.utils.Constants.LOREM_FLICKR_URL
import com.example.project2.utils.Constants.MOCKABLE_URL

object RetrofitClient {

    private val flowersRetroFit = Retrofit.Builder()
        .baseUrl(LOREM_FLICKR_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val mockableRetroFit = Retrofit.Builder()
        .baseUrl(MOCKABLE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val flowersConsumeApi= flowersRetroFit.create(FlowersConsumeApi::class.java)
    val mockableConsumeApi= mockableRetroFit.create(MockableConsumeApi::class.java)



}