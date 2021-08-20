package com.test.belajarviewbinding

import com.test.inka.model.DataResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<DataResponse>

    @FormUrlEncoded

    @POST("login_admin.php")
    fun loginAdmin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("name") name: String,
        @Field("birth") birth: String,
        @Field("gender") gender: String,
        @Field("desa_kelurahan") desaKelurahan: String,
        @Field("father") father: String,
        @Field("mother") mother: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("register_token.php")
    fun registerToken(
        @Field("id") id: String,
        @Field("token") token: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("logout.php")
    fun logout(
        @Field("id") id: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("delete_account.php")
    fun deleteAccount(
        @Field("id") id: String
    ): Call<DataResponse>

    @FormUrlEncoded
    @POST("edit_account.php")
    fun editAccount(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("birth") birth: String,
        @Field("gender") gender: String,
        @Field("desa_kelurahan") desaKelurahan: String,
        @Field("father") father: String,
        @Field("mother") mother: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<DataResponse>

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

    @GET("account.php")
    fun account(
    ): Call<DataResponse>

    @GET("get_notification.php")
    fun getNotification(
        @Query("id") id: String
    ): Call<DataResponse>

}