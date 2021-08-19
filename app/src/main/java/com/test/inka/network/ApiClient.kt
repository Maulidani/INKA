package com.toyota.toyserv.network

import com.test.belajarviewbinding.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val URL = "https://ancungproject.my.id/inka/"

    val instances: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiInterface::class.java)
    }

}