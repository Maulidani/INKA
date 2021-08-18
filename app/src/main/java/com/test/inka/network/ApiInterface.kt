package com.test.belajarviewbinding

import com.test.inka.model.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("request_vaccine.php")
    fun vaccineRequestPost(
        @Field("id") id: String,
        @Field("user") user: String,
        @Field("vaccine") vaccine: String,
        @Field("vaccined_date") vaccined: String,
        @Field("status") status: String
    ): Call<DataResponse>

    @GET("request_vaccine.php")
    fun vaccineRequest(
        @Query("user") user: String,
        @Query("status") status: String
        ): Call<DataResponse>

}