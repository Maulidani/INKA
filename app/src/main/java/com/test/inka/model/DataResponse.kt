package com.test.inka.model

data class DataResponse(
    val value: String,
    val message: String,
    val result: ArrayList<DataResult>
)

data class DataResult(
    val id: String,
    val name: String,
    val age_month: String,
    val vaccine_id: String,
    val vaccine_name: String,
    val desa_kelurahan_name: String,
    val vaccine_date: String,
    val status: String,
    val vaccined_date:String,
    var expendable: Boolean = false
)
