package com.test.belajarviewbinding

import com.test.inka.model.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

//    @FormUrlEncoded
//    @POST("login.php")
//    fun login(
//        @Field("type") type: String,
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): Call<DataResponse>

    @GET("request_vaccine.php")
    fun vaccineRequest(
        @Query("user") user: String,
        @Query("status") status: String
    ): Call<DataResponse>

}