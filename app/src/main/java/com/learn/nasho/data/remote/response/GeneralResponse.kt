package com.learn.nasho.data.remote.response

import com.google.gson.annotations.SerializedName

data class GeneralResponse(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    @field:SerializedName("message")
    val message: String? = null
)