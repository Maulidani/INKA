package com.test.inka.model

data class DataResponse(
    val value: String,
    val message: String,
    val result: ArrayList<DataResult>,

//    login
    val id: String,
    val desa_kelurahan: String,
    val name: String,
    val birth: String,
    val gender: String,
    val mother: String,
    val father: String,
    val username: String,
    val password: String,
    val desa_kelurahan_name: String,
    val vaccine_date: String

)

data class DataResult(
//    vaccine request
    val id: String,
    val name: String,
    val age_month: String,
    val vaccine_id: String,
    val vaccine_name: String,
    val desa_kelurahan_name: String,
    val vaccine_date: String,
    val status: String,
    val vaccined_date: String,

//    account
    val desa_kelurahan: String,
    val birth: String,
    val gender: String,
    val mother: String,
    val father: String,
    val username: String,
    val password: String,

//    notification
    val user: String,
    val title: String,
    val body: String,
    val date: String,
    val created_at: String,

    var expendable: Boolean = false
)
